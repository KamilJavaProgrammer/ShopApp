package ShopAppBackend.AMQP;

import ShopAppBackend.User.User;
import liquibase.pro.packaged.S;
import lombok.Data;

import java.io.Serializable;


@Data
public class Message implements Serializable {

    private String messageText;
    private String date;
    private String time;
    private User author;


}
