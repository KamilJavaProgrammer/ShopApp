package untiled.serwer.serweruntitled.Product.SubCategory;

import lombok.Data;
import untiled.serwer.serweruntitled.Product.Product;
import untiled.serwer.serweruntitled.Product.Category.Category;

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
