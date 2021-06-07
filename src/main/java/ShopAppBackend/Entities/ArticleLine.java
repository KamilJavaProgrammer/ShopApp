package ShopAppBackend.Entities;


import ShopAppBackend.Entities.Product;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "artcileLines")
@Data
public class ArticleLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Field name dont be empty")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
     private  List<Product> productList;

}
