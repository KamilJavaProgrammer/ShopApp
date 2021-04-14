package ShopAppBackend.CompleteOrder;


import ShopAppBackend.Client.ShopClient.ShopClientRepository;
import ShopAppBackend.Invoice.InvoiceService;
import ShopAppBackend.User.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ShopAppBackend.Adress.AddressRepo;
import ShopAppBackend.Business.BusinessRepo;
import ShopAppBackend.Client.ShopClient.ShopClient;
import ShopAppBackend.Company.Company;
import ShopAppBackend.Invoice.Invoice;
import ShopAppBackend.Invoice.InvoiceRepo;
import ShopAppBackend.ProductBasket.ProductBasketRepo;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
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
        System.out.println(shopClient);
        System.out.println(shopClient);
        System.out.println(shopClient);
        return shopClientRepository.existsShopClientByNameAndSurnameAndEmail(shopClient.getName(),
                shopClient.getSurname(),
                shopClient.getEmail());
    }


    @Transactional
    public HttpStatus AddOrderToDatabase(CompleteOrder completeOrder) throws IOException, SQLException {


        completeOrder.getProductsBasket().forEach(productBasketRepo::save);

        completeOrder.setState("W trakcie realizacji");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        completeOrder.setDate(simpleDateFormat.format(date));





            if(CheckExistsClient(completeOrder.getShopclient()))
            {
                ShopClient shopClient = this.shopClientRepository.findByNameAndSurnameAndEmail(completeOrder.getShopclient().getName(),
                                                                                   completeOrder.getShopclient().getSurname(),
                                                                                   completeOrder.getShopclient().getEmail());
                completeOrder.setShopclient(shopClient);
            }
            else

            {

                if (completeOrder.isToken())
                {
                    completeOrder.getShopclient().setState("Zarejestrowany");
                }
                else
                    {
                    completeOrder.getShopclient().setState("Niezarejestrowany");
                   }


                   addressRepo.save(completeOrder.getShopclient().getAddress());
                   addressRepo.save(completeOrder.getShopclient().getBusiness().getAddress());
                   businessRepo.save(completeOrder.getShopclient().getBusiness());
                   shopClientRepository.save(completeOrder.getShopclient());

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
                  invoice.setCompleteOrder(completeOrder);
                  invoiceRepo.save(invoice);

                completeOrder.setInvoiceVat(invoice);



                Company company = new Company();
            company.setName("CafeKam");
            company.setHeadquarters("ul.Czerniecka 123");
            company.setTown("Łącko");
            company.setNip("123456789");
            company.setAccount("02103232323232329");

           invoiceService.generatePDF(completeOrder,company);

            }

        completeOrderRepository.save(completeOrder);
        return null;
   }






   public List<CompleteOrder> GetAllOrdersByUser(String login){

        if(login!=null)
        {
            Long userId = userRepo.getUserId(login);
            Long clientId = userRepo.getClientId(userId);
            List<CompleteOrder> orders = completeOrderRepository.getAllOrder(clientId);
            return (orders != null) ? orders : Collections.emptyList();
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
