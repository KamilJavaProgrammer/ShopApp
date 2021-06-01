package ShopAppBackend.Entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "newsletter")
@Data
public class NewsLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String  email;

    public NewsLetter(){}

    public NewsLetter(String email) {
        this.email = email;
    }
}
