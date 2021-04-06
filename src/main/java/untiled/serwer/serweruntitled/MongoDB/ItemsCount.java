package untiled.serwer.serweruntitled.MongoDB;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.swing.event.InternalFrameEvent;
import java.util.List;

@Document
public class ItemsCount {

    @Id
    private String id;
    private Integer number;
    private List<Integer> amountOfElements;

    public ItemsCount() {
    }

    public ItemsCount(String id,List<Integer> amountOfElements) {

        this.amountOfElements = amountOfElements;
        this.id = id;
    }
    public ItemsCount(String id,Integer number)
    {
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmountOfElements(List<Integer> amountOfElements) {
        this.amountOfElements = amountOfElements;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Integer> getAmountOfElements() {
        return amountOfElements;
    }

    @Override
    public String toString() {
        return "ItemsCount{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", amountOfElements=" + amountOfElements +
                '}';
    }
}
