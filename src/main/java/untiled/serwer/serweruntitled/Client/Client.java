package untiled.serwer.serweruntitled.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import untiled.serwer.serweruntitled.Adress.Address;
import untiled.serwer.serweruntitled.Business.Business;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrder;
import untiled.serwer.serweruntitled.Invoice.Invoice;
import untiled.serwer.serweruntitled.User.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@MappedSuperclass
@Data
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name doesnt empty")
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








