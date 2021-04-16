package ShopAppBackend.FrontMainPageManagement.Section.SectionCategory;


import ShopAppBackend.FrontMainPageManagement.Section.Section;
import ShopAppBackend.FrontMainPageManagement.Section.SectionSubCategory.SectionSubCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sectionsCategories")
@Data
public class SectionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    private Section section;

    @OneToMany
    private List<SectionSubCategory> sectionSubCategoriesList;
}
