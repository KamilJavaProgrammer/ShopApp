package untiled.serwer.serweruntitled.Client.ServiceClient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.Device.Device;

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
