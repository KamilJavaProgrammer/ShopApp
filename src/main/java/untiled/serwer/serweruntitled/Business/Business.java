package untiled.serwer.serweruntitled.Business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import untiled.serwer.serweruntitled.Adress.Address;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.Invoice.Invoice;

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


    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "business")
    private List<Invoice> invoices;
}
