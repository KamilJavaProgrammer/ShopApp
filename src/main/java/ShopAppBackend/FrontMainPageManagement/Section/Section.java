package ShopAppBackend.FrontMainPageManagement.Section;

import ShopAppBackend.FrontMainPageManagement.Section.SectionCategory.SectionCategory;
import ShopAppBackend.FrontMainPageManagement.Section.SectionSubCategory.SectionSubCategory;
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
