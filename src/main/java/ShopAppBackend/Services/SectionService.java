package ShopAppBackend.Services;


import ShopAppBackend.Entities.Section;
import ShopAppBackend.Entities.SectionCategory;
import ShopAppBackend.Repositories.SectionCategoryRepository;
import ShopAppBackend.Entities.SectionSubCategory;
import ShopAppBackend.Repositories.SectionSubCategoryRepository;
import ShopAppBackend.Repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SectionService {


    private final SectionSubCategoryRepository sectionSubCategoryRepository;
    private final SectionCategoryRepository sectionCategoryRepository;
    private final SectionRepository sectionRepository;


    @Autowired
    public SectionService(SectionSubCategoryRepository sectionSubCategoryRepository, SectionCategoryRepository sectionCategoryRepository, SectionRepository sectionRepository) {
        this.sectionSubCategoryRepository = sectionSubCategoryRepository;
        this.sectionCategoryRepository = sectionCategoryRepository;
        this.sectionRepository = sectionRepository;
    }

    public ResponseEntity<List<Section>> GetAllSectionsFromDatabase() {
        List<Section> sections = sectionRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sections);
    }

    @Transactional
    public ResponseEntity<HttpStatus> AddOneSectionToDatabase(Section section) {

        if (section != null) {

            List<SectionCategory> sectionCategories = new ArrayList<>();
            for(SectionCategory sectionCategory: section.getSectionCategoriesList()) {

                List<SectionSubCategory> sectionSubCategories = new ArrayList<>();

                for (SectionSubCategory sectionSubCategory : sectionCategory.getSectionSubCategoriesList())

                {
                    SectionSubCategory sectionSubCategoryInstant = new SectionSubCategory();
                    sectionSubCategoryInstant.setName(sectionSubCategory.getName());
                    sectionSubCategoryRepository.save(sectionSubCategoryInstant);
                    sectionSubCategories.add(sectionSubCategoryInstant);
                }

                SectionCategory sectionCategoryInstant = new SectionCategory();
                sectionCategoryInstant.setName(sectionCategory.getName());

               sectionCategoryInstant.setSectionSubCategoriesList(sectionSubCategories);
                sectionCategoryRepository.save(sectionCategoryInstant);
                sectionCategories.add(sectionCategoryInstant);

            }

            Section sectionInstant = new Section();
            sectionInstant.setName(section.getName());
            sectionInstant.setSectionCategoriesList(sectionCategories);
            sectionRepository.save(sectionInstant);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);

        }
        else {
            throw new IllegalArgumentException();
        }
    }



    @Transactional
    public ResponseEntity<HttpStatus> DeleteOneSectionById(Integer id){

        sectionRepository.deleteById(id);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}



