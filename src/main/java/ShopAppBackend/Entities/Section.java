package ShopAppBackend.Entities;

import ShopAppBackend.Entities.SectionCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sections")
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

   @OneToMany
    private List<SectionCategory> sectionCategoriesList;

}
