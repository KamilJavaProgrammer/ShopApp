package ShopAppBackend.ServicePriceList;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "services")
@Data
public class ServicePriceList implements Comparable<ServicePriceList> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String name;
    private Double price;

    @Override
    public int compareTo(ServicePriceList o) {
       return name.compareTo(o.name);
    }
}
