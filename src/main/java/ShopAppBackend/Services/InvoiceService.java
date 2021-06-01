package ShopAppBackend.Services;
import ShopAppBackend.Entities.Invoice;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Repositories.ProductRepo;
import ShopAppBackend.Repositories.InvoiceRepo;
import com.lowagie.text.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ShopAppBackend.Repositories.BusinessRepo;
import ShopAppBackend.Entities.Company;
import ShopAppBackend.Entities.CompleteOrder;
import ShopAppBackend.Repositories.CompleteOrderRepository;
import ShopAppBackend.Entities.ProductBasket;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


@Service
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;
    private BusinessRepo businessRepo;
    private JavaMailSender javaMailSender;
    private final ProductRepo productRepo;
    private ModelMapper modelMapper;
    private final CompleteOrderRepository completeOrderRepository;
    private final UserService userService;


    @Autowired
    public InvoiceService(InvoiceRepo invoiceRepo, BusinessRepo businessRepo, JavaMailSender javaMailSender, ProductRepo productRepo, ModelMapper modelMapper, CompleteOrderRepository completeOrderRepository,UserService userService) {
        this.invoiceRepo = invoiceRepo;
        this.businessRepo = businessRepo;
        this.javaMailSender = javaMailSender;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.completeOrderRepository = completeOrderRepository;
        this.userService = userService;
    }


    int lp;

    String html1;
    String html2;
    String html3;
    String html0;
    String nameOfProduct;
    Integer quantity;
    String discount;
    String vatRate;
    String unit;

    double nettoValue;
    double vatValue;
    double bruttovalue;
    double nettoPrice;
    double netto;
    double vat;
    double brutto;


    public  void generatePDF(Invoice invoice, Company company) throws IOException, SQLException {


        String date = LocalDate.now().toString();
        String[] helpTable = date.split("-");

        String invoiceNumber = helpTable[2] + "/" + helpTable[1] + "/" + helpTable[0];
        String nip = invoice.getBusiness().getNip();
        String address = invoice.getBusiness().getAddress().getPlaceOfresident();
        String nameBusinnes = invoice.getBusiness().getName();

        html1 = "";
        html2 = "";
        lp = 0;
        this.brutto = 0;
        this.vat = 0;
        this.netto = 0;

        for(ProductBasket productBasket :invoice.getProductBaskets())
        {
            System.out.println(productBasket);

            this.lp = lp+1;
            this.nameOfProduct = productBasket.getNameOfProduct();
            this.quantity = productBasket.getNumberOfItems();
            this.unit = productBasket.getUnit();
            this.nettoPrice = Math.round(productBasket.getBruttoPrice()/1.23);
            this.discount = productBasket.getDiscount();
            this.vatRate = productBasket.getVatRate();
            this.nettoValue = Math.round(nettoPrice * quantity);
            this.vatValue = Math.round(0.23 * bruttovalue);
            this.bruttovalue = Math.round(nettoPrice * quantity * 1.23);

            this.brutto +=Math.round(bruttovalue);
            this.vat += Math.round(vatValue);
            this.netto += Math.round(nettoValue);


            Product product  = this.productRepo.getOne(productBasket.getIdProduct().longValue());

            product.setNumberOfItems(product.getNumberOfItems() - productBasket.getNumberOfItems());

            String htmlTableValue = "<tr>" +
                    "          <td style = \"border:1px solid black; width:2%;text-align:center;\" >"+lp+"</td>" +
                    "          <td style = \"border:1px solid black; width:15%;text-align:center;\"  >"+nameOfProduct+"</td>" +
                    "          <td style = \"border:1px solid black; width:5%;text-align:center;\" >"+quantity+"</td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\"  >"+unit+"</td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+nettoPrice+" </td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+discount+" </td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+vatRate+" </td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+nettoValue+" </td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+vatValue+" </td>" +
                    "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >"+bruttovalue+" </td>" +
                    "        </tr>";

            html1 = html1 + htmlTableValue;
        }


        html0 = "  <div style = ' width:33.3%; float:left; border-bottom: 2px solid black;" +
                "  font-size: 20px; font-weight: 600; margin-top:20px; text-align:center;'>" +
                " Sprzedawca </div>" +
                "<div style='width:33.3%; visibility:hidden; float:left; '>xyzabcde</div>"+

                "  <div style = 'width:33.3%; float:left; border-bottom: 2px solid black;" +
                "  font-size: 20px; font-weight: 600; margin-top:20px; text-align: center;'>" +
                " Nabywca </div>" +

               " <div style= 'text-align:left; width:33.3%; float:left; font-size:20px; font-weight:400 clear:both;'> " +
                " <br></br>" + company.getName() +
                "     <br></br>" + company.getHeadquarters() +
                "     <br></br>" + company.getNip() +
                "    </div>  " +

                "<div style='width:33.3%;  visibility:hidden; float:left; '>xyzabcde</div>"+

                " <div style= 'text-align:left; float:left; width:33.3%; font-size:20px; font-weight:400; '> " +

                                " <br> </br>" + nameBusinnes +
                "     <br>  </br>" + address +
                "     <br></br>" + nip +
                "    </div>  " ;


        html2 = "<div style = \"width:50%;clear:both; float:left; padding-top:30px;\"><table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" ></td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >Wartość Netto</td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >Wartość Vat</td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >Wartość Brutto</td>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >Razem</td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >"+netto+"</td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >"+vat+"</td>\n" +
                "<td style = \"border:1px solid black; width:2%;text-align:center;\" >"+brutto+"</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table> </div>" +
                " <div style = 'width:40%;float:left; margin-left:40px; font-size:18px; padding-top:35px;'>" +
                " <span>Sposob platnosci :poo</span> <br> </br>"+
                " <span>Termin platnosci : ooip</span>" + "<br> </br>"+
                " <span>Konto : po09</span></div>";


        html3 = "    <div style = 'clear:both; margin-top: 150px; background-color:#c2c2a3 ;" +
                   "  font-weight: 500; text-align: center; font-size: 24px; width:100%'>" +
                   "      Razem do zaplaty " + " "+brutto+" PLN </div>";



            try {

                String key = this.userService.GenerateRandomKey();

               Path path = Files.createFile(Paths.get("C:/Rozliczenia/" + invoice.getBusiness().getName() + key+ ".pdf"));


//               completeOrder.getInvoiceVat().setInvoicePath(path.toString());
//               completeOrder.getInvoiceVat().setInvoicePath(path.toString());

               OutputStream out = new FileOutputStream(path.toString());
                ITextRenderer renderer = new ITextRenderer();

                renderer.setDocumentFromString("<div style = \" width:100%;\">   <div style=\"margin-top: 20px;width:100%;\">\n" +
                        "    <div style=\"color: black; width:50%;\n" +
                        "  background-color:#c2c2a3;\n" +
                        "  font-size:25px;\n" +
                        "  border-bottom:1px solid black;\n" +
                        "  border-top:1px solid black;float: left;\">\n" +
                        "      Faktura nr" + " " + invoiceNumber +
                        "    </div>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    <div style=\" color: black; width:50%;\n" +
                        "         background-color: #c2c2a3;\n" +
                        "         font-size: 25px;\n" +
                        "         border-bottom: 1px solid black;\n" +
                        "         border-top: 1px solid black; float: left;\">\n" +
                        "      Data wystawienia" + " " + date +
                        "    </div>\n" +
                        "  </div>" +
                        html0+

                        " <div style = \"width:100%;clear:both; padding-top:30px;\">\n" +
                        "      <table>\n" +
                        "        <thead>\n" +
                        "        <tr>\n" +
                        "          <td style = \"border:1px solid black; width:2%;text-align:center;\" >lp</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\"  >Nazwa</td>\n" +
                        "          <td  style = \"border:1px solid black; width:10%;text-align:center;\" >Ilosc</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Jdn</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\"  >Cena netto</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Rabat</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Stawka Vat</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Wart.netto.</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Wart.vat.</td>\n" +
                        "          <td style = \"border:1px solid black; width:10%;text-align:center;\" >Wart.brutto.</td>\n" +
                        " \n" +
                        "        </tr>\n" +
                        "\n" +
                        "        </thead>\n" +
                        "\n" +
                        "        <tbody>\n" +
                        html1+
                        "\n" +
                        "\n" +
                        "        </tbody>\n" +
                        "      </table>\n" +
                        "    </div> " +
                        html2+
                        html3+
                        " </div>");
                renderer.layout();
                renderer.createPDF(out);

                out.close();


            } catch (IOException | DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    public void SendInvoiceToClientEmail(String email,String fileName) throws MessagingException {
                        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setSubject("PDF");
                FileSystemResource file = new FileSystemResource(
                        new File("C:/Rozliczenia/" + fileName + ".pdf"));
                mimeMessageHelper.addAttachment("Faktura Vat",file,"application/pdf");
                mimeMessageHelper.setText("Dziękujemy za zakupy w firmie CafeKam.W załączniku przesyłamy fakturę Vat", false);
                javaMailSender.send(mimeMessage);
    }



        public ResponseEntity<InputStreamResource> GetInvoiceById(Long id) throws FileNotFoundException {

           CompleteOrder completeOrder = completeOrderRepository.getOne(id);
            String path = completeOrder.getInvoiceVat().getInvoicePath();

            File file = new File(path);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);


    }


    public ResponseEntity<List<Invoice>> GetAllInvoices() {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceRepo.findAll());
    }
}


