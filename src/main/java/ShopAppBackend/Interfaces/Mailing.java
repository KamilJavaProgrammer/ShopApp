package ShopAppBackend.Interfaces;
//import javax.mail.MessagingException;


import javax.mail.MessagingException;

@FunctionalInterface
public interface Mailing {

     void Mail() throws InterruptedException, MessagingException;

}
