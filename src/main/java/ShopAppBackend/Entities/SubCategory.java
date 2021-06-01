package ShopAppBackend.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subcategories")
@Data

public class SubCategory implements Comparable<SubCategory> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<Product> product;

    @Override
    public int compareTo(SubCategory o) {
        return 0;
    }
}
