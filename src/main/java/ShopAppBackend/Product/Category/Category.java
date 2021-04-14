package ShopAppBackend.Product.Category;

import ShopAppBackend.Product.Product;
import lombok.Data;
import ShopAppBackend.Product.SubCategory.SubCategory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany
    private List<Product> product;

    @OneToMany
    private List<SubCategory> subCategories;

}
