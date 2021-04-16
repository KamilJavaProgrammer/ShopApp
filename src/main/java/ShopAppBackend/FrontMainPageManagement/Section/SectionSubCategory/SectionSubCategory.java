package ShopAppBackend.FrontMainPageManagement.Section.SectionSubCategory;


import ShopAppBackend.FrontMainPageManagement.Section.Section;
import lombok.Data;

import javax.persistence.*;
import java.util.Enumeration;

@Entity
@Table(name = "sectionsSubCategories")
@Data
public class SectionSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    private SectionSubCategory sectionSubCategory;

}

