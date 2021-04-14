package ShopAppBackend.ArtcilesOnInvoice;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "articlesOnInvoice")
@Data
public class ArticlesOnInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private String nameOfProduct;
    private String cod;
    private String unit;
    private Integer quantity;
    private Integer discount;
    private String rateVat;
    private double nettoPrice;
    private double nettoValue;
    private double vatValue;
    private double bruttoValue;
    public ArticlesOnInvoice() {
    }


}
