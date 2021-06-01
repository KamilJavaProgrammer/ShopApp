package ShopAppBackend.Entities;

import ShopAppBackend.AbstractSuperclass.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "shopClients")
@Data
public class ShopClient extends Client implements Comparable<ShopClient>, Serializable {

    private String state;
    private String email;


    @JsonIgnore
    @OneToMany(mappedBy = "shopclient",fetch = FetchType.LAZY)
    private List<CompleteOrder> completeOrder;

    @Override
    public int compareTo(ShopClient o) {
        if(getName() == null)
        {
            return 0;
        }
        return  getName().compareTo(o.getName());
    }


//    @JsonIgnore
//    @OneToOne(mappedBy = "client")
//    private User user;


}
