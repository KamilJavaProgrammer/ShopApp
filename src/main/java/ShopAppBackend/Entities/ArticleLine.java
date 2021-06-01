package ShopAppBackend.Entities;


import ShopAppBackend.Entities.Product;
import lombok.Data;

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
