package ShopAppBackend.Upload;

import ShopAppBackend.Product.Product;

public class UploadEntity {


    private Long id;
    private String PathTofile;

    private Product product;

    public UploadEntity(){}

    public UploadEntity(Long id, String pathTofile, Product product) {
        this.id = id;
        PathTofile = pathTofile;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathTofile() {
        return PathTofile;
    }

    public void setPathTofile(String pathTofile) {
        PathTofile = pathTofile;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "UploadEntity{" +
                "id=" + id +
                ", PathTofile='" + PathTofile + '\'' +
                ", productEntity=" + product +
                '}';
    }
}
