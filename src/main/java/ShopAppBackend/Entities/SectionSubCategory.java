package ShopAppBackend.Entities;


import lombok.Data;

import javax.persistence.*;

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

