package untiled.serwer.serweruntitled.Product;

public class ProductDTO {

    private String name;
    private int numberOfItems;
    private String state;
    private String pathToFile;
    private String technicalData;
    private String price;
    private String model;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getTechnicalData() {
        return technicalData;
    }

    public void setTechnicalData(String technicalData) {
        this.technicalData = technicalData;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", numberOfItems=" + numberOfItems +
                ", state='" + state + '\'' +
                ", pathToFile='" + pathToFile + '\'' +
                ", technicalData='" + technicalData + '\'' +
                ", price='" + price + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
