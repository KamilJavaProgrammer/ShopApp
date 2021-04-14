package ShopAppBackend.Client.ServiceClient;

import ShopAppBackend.Device.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ShopAppBackend.Client.Client;

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
