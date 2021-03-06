package ShopAppBackend.Services;


import ShopAppBackend.Entities.ArticleLine;
import ShopAppBackend.Entities.Section;
import ShopAppBackend.Entities.SectionCategory;
import ShopAppBackend.Exceptions.ArticleLineNotFoundException;
import ShopAppBackend.Exceptions.SectionNotFoundException;
import ShopAppBackend.Logs.LogsApplication;
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
    private final LogsApplication logsApplication;


    @Autowired
    public SectionService(LogsApplication logsApplication,SectionSubCategoryRepository sectionSubCategoryRepository, SectionCategoryRepository sectionCategoryRepository, SectionRepository sectionRepository) {
        this.sectionSubCategoryRepository = sectionSubCategoryRepository;
        this.sectionCategoryRepository = sectionCategoryRepository;
        this.sectionRepository = sectionRepository;
        this.logsApplication = logsApplication;
    }

    public List<Section> GetAllSectionsFromDatabase() {
        List<Section> sections = sectionRepository.findAll();
        return (sections.isEmpty()) ? Collections.emptyList(): sections;

    }

    @Transactional
    public HttpStatus AddOneSectionToDatabase(Section section) {

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
            this.logsApplication.SaveLogToDatabase("Add new Menu section");
            return HttpStatus.NO_CONTENT;

        }
        else {
            throw new IllegalArgumentException();
        }
    }



    @Transactional
    public HttpStatus DeleteOneSectionById(Integer id){
        Optional<Section> section = sectionRepository.findById(id);
        sectionRepository.delete(section.orElseThrow(SectionNotFoundException::new));
        return HttpStatus.NO_CONTENT;
    }

}



