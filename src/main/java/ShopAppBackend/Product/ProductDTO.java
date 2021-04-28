package ShopAppBackend.Product;


import lombok.Data;

@Data
public class ProductDTO {

    private String name;
    private int numberOfItems;
    private String state;
    private String pathToFile;
    private String technicalData;
    private String price;
    private String model;

}
