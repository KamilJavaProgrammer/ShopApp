package ShopAppBackend.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nip;
    private String account;
    private String email;
    private String phoneNumber;
    private String headquarters;
    private String town;
    private String codePost;



}
