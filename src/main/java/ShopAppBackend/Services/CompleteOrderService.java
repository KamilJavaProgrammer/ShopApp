package ShopAppBackend.Services;


import ShopAppBackend.Entities.CompleteOrder;
import ShopAppBackend.Repositories.*;
import ShopAppBackend.Repositories.ShopClientRepository;
import com.auth0.jwt.JWT;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ShopAppBackend.Entities.ShopClient;
import ShopAppBackend.Entities.Company;
import ShopAppBackend.Entities.Invoice;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Service
public class CompleteOrderService {

   private final CompleteOrderRepository completeOrderRepository;
   private final ShopClientRepository shopClientRepository;
   private final ProductBasketRepo productBasketRepo;
   private final ModelMapper modelMapper;
   private final InvoiceRepo invoiceRepo;
   private final  AddressRepo addressRepo;
   private final  BusinessRepo businessRepo;
   private final InvoiceService invoiceService;
   private final UserRepo userRepo;



   @Autowired
    public CompleteOrderService(CompleteOrderRepository completeOrderRepository, ShopClientRepository shopClientRepository,
                                ProductBasketRepo productBasketRepo, ModelMapper modelMapper, InvoiceRepo invoiceRepo,
                                AddressRepo addressRepo, BusinessRepo businessRepo, InvoiceService invoiceService,
                                UserRepo userRepo) {

        this.completeOrderRepository = completeOrderRepository;
        this.shopClientRepository = shopClientRepository;
        this.productBasketRepo = productBasketRepo;
        this.modelMapper = modelMapper;
        this.invoiceRepo = invoiceRepo;
        this.addressRepo = addressRepo;
        this.businessRepo = businessRepo;
        this.invoiceService = invoiceService;
        this.userRepo = userRepo;
    }


    public boolean CheckExistsClient(ShopClient shopClient){
       return shopClientRepository.existsShopClientByNameAndSurnameAndEmail(shopClient.getName(),
                shopClient.getSurname(),
                shopClient.getEmail());
    }



    public void AddNoRegisterShopClient(CompleteOrder completeOrder){
        completeOrder.getShopclient().setState("Niezarejestrowany");
        addressRepo.save(completeOrder.getShopclient().getAddress());
        addressRepo.save(completeOrder.getShopclient().getBusiness().getAddress());
        businessRepo.save(completeOrder.getShopclient().getBusiness());
        shopClientRepository.save(completeOrder.getShopclient());

    }


    @Transactional
    public HttpStatus AddOrderToDatabase(CompleteOrder completeOrder) throws IOException, SQLException {


        completeOrder.getProductsBasket().forEach(productBasketRepo::save);

        completeOrder.setState("W trakcie realizacji");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        completeOrder.setDate(simpleDateFormat.format(date));

         if(completeOrder.getToken() == null)
         {
           AddNoRegisterShopClient(completeOrder);
         }
         else
         {
             if(JWT.decode(completeOrder.getToken()).getExpiresAt().before(Date.from(Instant.now()))){

                 if(CheckExistsClient(completeOrder.getShopclient()))
                 {
                     ShopClient shopClient = this.shopClientRepository.findByNameAndSurnameAndEmail(completeOrder.getShopclient().getName(),
                             completeOrder.getShopclient().getSurname(),
                             completeOrder.getShopclient().getEmail());
                             completeOrder.setShopclient(shopClient);
                 }

                 else

                 {
                     AddNoRegisterShopClient(completeOrder);
                 }

             }

         }

                  addressRepo.save(completeOrder.getAddress());



            if(completeOrder.isInvoice()) {
                    Invoice invoice = new Invoice();
                    invoice.setDate(LocalDate.now().toString());
                    invoice.setPaid("0");
                    invoice.setPayForm(completeOrder.getPaymentMethod());
                    invoice.setSpendFromStock("Magazyn głowny");


                    if(completeOrder.getPaymentMethod().equals("Pobranie"))
                    {
                        invoice.setPaymentDeadline("0 dni");
                    }
                    else
                    {
                        invoice.setPaymentDeadline(" 7 dni");
                    }

                  invoice.setSumBruttoValue(completeOrder.getSumPaid());

                    DecimalFormat decimalFormat = new DecimalFormat();
                    decimalFormat.setMaximumFractionDigits(2);
                    decimalFormat.setMinimumFractionDigits(1);


                  String sumNettoValue = decimalFormat.format(completeOrder.getSumPaid()/1.23);
                  invoice.setSumNettoValue(Double.parseDouble(sumNettoValue.replace(",",".").
                                                                                                           replace(" ","")));


                  String sumVatValue = decimalFormat.format(0.23*completeOrder.getSumPaid());
                  invoice.setSumVatValue(Double.parseDouble(sumVatValue.replace(",",".").replace(" ","")));



                  invoice.setShopClient(completeOrder.getShopclient());
                  invoice.setBusiness(completeOrder.getShopclient().getBusiness());
//                  invoice.setBusiness(Collections.singletonList(completeOrder.getShopclient().getBusiness()));
                  invoice.setCompleteOrder(completeOrder);
                  invoiceRepo.save(invoice);

                completeOrder.setInvoiceVat(invoice);



                Company company = new Company();
            company.setName("CafeKam");
            company.setHeadquarters("ul.Czerniecka 123");
            company.setTown("Łącko");
            company.setNip("123456789");
            company.setAccount("02103232323232329");

//           invoiceService.generatePDF(completeOrder,company);

            }

        completeOrderRepository.save(completeOrder);
        return null;
   }






   public ResponseEntity<List<CompleteOrder>> GetAllOrdersByUser(String username){

        if(username!=null)
        {
            Long clientId = userRepo.getClientIdByUserUsername(username);
            List<CompleteOrder> orders = completeOrderRepository.getAllOrder(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
        else
        {

            throw new IllegalArgumentException();
        }

    }

    public List<CompleteOrder> GetAllOrders(){
       List<CompleteOrder> completeOrders = completeOrderRepository.findAll();
       completeOrders.sort(CompleteOrder::compareToDate);
        return completeOrders;
    }
}
