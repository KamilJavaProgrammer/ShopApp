package ShopAppBackend.Message;

import ShopAppBackend.User.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;



@Data
@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageText;
    private String date;
    private String state;

    @Transient
    private String recipient;


    public Message(){}

    public Message(String messageText, String date, String state) {
        this.messageText = messageText;
        this.date = date;
        this.state = state;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

}
