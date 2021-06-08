package ShopAppBackend.Services;

import ShopAppBackend.Entities.NewsLetter;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.NewsletterRepository;
import ShopAppBackend.Entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

@Service
public class NewsLetterService  {

    private final NewsletterRepository newsletterRepository;
    private final JavaMailSender javaMailSender;
    private static final String SUBJECT = "Nowy produkt";
    private static final String DESCRIPTION = "Uwaga dodaliśmy nowy produkt";
    private final LogsApplication logsApplication;

    @Autowired
    public NewsLetterService(NewsletterRepository newsletterRepository, JavaMailSender javaMailSender,LogsApplication logsApplication) {
        this.newsletterRepository = newsletterRepository;
        this.javaMailSender = javaMailSender;
        this.logsApplication = logsApplication;
    }

    @Transactional
    public HttpStatus AttachObserver(String email){
         newsletterRepository.save(new NewsLetter(email));
         this.logsApplication.SaveLogToDatabase("Attach new Observer to Newsletter" + email);
         return HttpStatus.CREATED;
    }

    @Transactional
    public HttpStatus RemoveObserver(Integer id){
        Optional<NewsLetter> newsLetter = newsletterRepository.findById(id);
        if(newsLetter.isPresent())
        {
            newsletterRepository.deleteById(id);
            this.logsApplication.SaveLogToDatabase("Delete  Observer id = ");
            return HttpStatus.NO_CONTENT;
        }
        else
        {
            return  HttpStatus.NOT_FOUND;
        }

    }

    public List<NewsLetter> GetAllNewsletters(){
        return newsletterRepository.findAll();
    }

    public void SendEmail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);

    }


    @Async
    public void NotifyAll(Product product){
        List<String> emails = newsletterRepository.getEmailsSubscribers();
        emails.forEach(email -> {
            try {
                SendEmail(email,SUBJECT,DESCRIPTION + product.getProductName() + product.getDescription(),false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        this.logsApplication.SaveLogToDatabase("Send Emails to Subscribers");

    }


   //Rx-Java//
    public Observable<?> SendMailsToSubcscribersRXJAVA(List<String> emails,Product product) {

        return Observable.fromCallable(() -> {

            emails.forEach(email -> {
                try {
                    SendEmail(email,SUBJECT,DESCRIPTION + product.getProductName() + product.getDescription(),false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            return null;
        }).subscribeOn(Schedulers.io());
    }

    Scheduler scheduler(int n) {
        return Schedulers.from(Executors.newFixedThreadPool(n));
    }

}
