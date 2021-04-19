package ShopAppBackend.FrontMainPageManagement.ArticleLine;


import ShopAppBackend.Product.Product;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artcileLines")
@Data
public class ArticleLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
     private  List<Product> productList;

}
