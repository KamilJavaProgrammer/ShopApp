package untiled.serwer.serweruntitled.Adress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import untiled.serwer.serweruntitled.Business.Business;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrder;

import javax.persistence.*;

@Entity
@Table(name = "adresses")
@Data
public class Address implements Comparable<Address> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placeOfresident;
    private String town;
    private String postCode;
    private String type;

    public Address(Long id, String placeOfresident, String town, String postCode, String type) {
        this.id = id;
        this.placeOfresident = placeOfresident;
        this.town = town;
        this.postCode = postCode;
        this.type = type;
    }

    public Address() {
    }

    @Override
    public int compareTo(Address o) {
        return 0;
    }
}


