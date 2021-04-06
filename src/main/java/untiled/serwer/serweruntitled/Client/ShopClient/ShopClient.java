package untiled.serwer.serweruntitled.Client.ShopClient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrder;
import untiled.serwer.serweruntitled.User.User;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "shopClients")
@Data
public class ShopClient extends Client implements Comparable<ShopClient>{

    private String state;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "shopclient",fetch = FetchType.LAZY)
    private List<CompleteOrder> completeOrder;

    @Override
    public int compareTo(ShopClient o) {
        return  getName().compareTo(o.getName());
    }


//    @JsonIgnore
//    @OneToOne(mappedBy = "client")
//    private User user;


}
