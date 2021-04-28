package ShopAppBackend.Business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ShopAppBackend.Adress.Address;
import ShopAppBackend.Invoice.Invoice;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "firms")
@Data
public class Business  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nip;
    private String account;
    private String email;
    private String regon;
    private String phoneNumber;


    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
//
//    @ManyToMany(mappedBy = "business")
//    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "business")
    private List<Invoice> invoices;
}
