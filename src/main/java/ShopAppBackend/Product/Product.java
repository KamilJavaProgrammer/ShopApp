package ShopAppBackend.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;
import ShopAppBackend.Product.Category.Category;
import ShopAppBackend.Product.SubCategory.SubCategory;

import javax.persistence.*;

@Entity
@Table(name="products")
@Proxy(lazy = false)
@Data
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String productCategory;
    private String productSubCategory;
    private String productName;
    private String manufacturer;
    private String serialNumber;
    private String model;
    private String productPrice;
    private String pathToFile;
    private Integer numberOfItems;
    private String location;
    private String cod;
    private String status;
    private String description;
    private String wareHouseplace;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subcategoryid")
    private SubCategory subCategory;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category1;

    @Transient
    private int quantity;




    @Override
    public int compareTo(Product o) {
        return this.productName.compareTo(o.getProductName());
    }


    public int compareToPrice(Product o) {
        if(Double.parseDouble(this.productPrice) > Double.parseDouble(o.getProductPrice()))
        {
           return 1;
        }

        else if(Double.parseDouble(this.productPrice) < Double.parseDouble(o.getProductPrice()))
        {
            return -1;
        }
        return 0;
    }
}



