package ShopAppBackend.Entities;


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
