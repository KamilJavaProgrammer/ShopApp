package ShopAppBackend.Entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ShopAppBackend.AbstractSuperclass.Client;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "serviceClient")
@Data
public class ServiceClient extends Client {

    @OneToMany
    private List<Device> deviceList;
}
