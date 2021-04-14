package ShopAppBackend.Device;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "devices")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String manufacturer;
    private String model;
    private String serialNumber;

}
