package ShopAppBackend.Product.SubCategory;

import ShopAppBackend.Product.Product;
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

    public SubCategory(){}

    public SubCategory(Long id, String name, List<Product> product ) {
        this.id = id;
        this.name = name;
        this.product = product;
    }


    @Override
    public int compareTo(SubCategory o) {
        return 0;
    }
}