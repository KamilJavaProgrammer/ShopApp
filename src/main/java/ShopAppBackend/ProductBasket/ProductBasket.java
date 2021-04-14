package ShopAppBackend.ProductBasket;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "productBasket")
@Data
public class ProductBasket {

    @Id
    @GeneratedValue
    private  Long id;
    private Integer idProduct;
    private String nameOfProduct;
    private Integer numberOfItems;
    private double nettoPrice;
    private double bruttoPrice;
    private String discount;
    private String vatRate;
    private String unit;


}
