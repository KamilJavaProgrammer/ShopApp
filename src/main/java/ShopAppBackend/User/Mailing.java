package ShopAppBackend.User;

import javax.mail.MessagingException;


@FunctionalInterface
public interface Mailing {
     void Mail() throws InterruptedException, MessagingException;
}