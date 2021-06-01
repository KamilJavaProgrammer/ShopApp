package ShopAppBackend.AbstractSuperclass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ShopAppBackend.Entities.Address;
import ShopAppBackend.Entities.Business;
import ShopAppBackend.Entities.Invoice;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
@Data
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotEmpty(message = "Name doesnt empty")
    private String name;
    private String surname;
    private String phoneNumber;


    @OneToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Invoice> invoices;

}








