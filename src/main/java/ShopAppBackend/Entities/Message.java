package ShopAppBackend.Entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "messages")
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String messageText;
    private String date;
    private String state;

    public Message(Long id, String messageText, String date, String state) {
        this.id = id;
        this.messageText = messageText;
        this.date = date;
        this.state = state;
    }

    @JsonIgnoreProperties("messages")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @JsonIgnoreProperties("messages")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;


}
