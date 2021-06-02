package ShopAppBackend.DocumentsMongoDB;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @Id
    private String id;
    private List<String> data;

}
