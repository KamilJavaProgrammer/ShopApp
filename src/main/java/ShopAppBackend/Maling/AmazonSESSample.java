package ShopAppBackend.Maling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class AmazonSESSample {

    private final String senderMail;
    private final String senderName;
    private final String smtpUsername;
    private final String smtpPassword;
    static final int PORT = 587;
    static final String CONFIGSET = "ConfigSet";
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";



    @Autowired
    AmazonSESSample(@Value("${sender.email}")String senderMail, @Value("${sender.name}")String senderName,
                    @Value("${smtp.username}")String smtpUsername, @Value("${smtp.password}")String smtpPassword){
        this.senderMail = senderMail;
        this.senderName = senderName;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
    }

    @Async
    public  void SendEmail(String recipient,String subject,String body) throws Exception {

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderMail,senderName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setContent(body,"text/plain");
        String encodingOptions = "text/html; charset=UTF-8";

//        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
        msg.setHeader("Content-Type", encodingOptions);

        Transport transport = session.getTransport();

        try
        {
            System.out.println("Sending...");

            transport.connect(HOST, smtpUsername, smtpPassword);

            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            transport.close();
        }
    }
}
