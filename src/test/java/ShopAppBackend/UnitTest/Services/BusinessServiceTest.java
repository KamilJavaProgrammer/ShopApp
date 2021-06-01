package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Entities.Business;
import ShopAppBackend.Repositories.BusinessRepo;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceTest {


    @Mock
    Business business;
    @Mock
    BusinessRepo businessRepo;



//    @Test
//    public void SaveBusinessToDatabase1_businessNull_throwsNullPointerException(){
//        BusinessService businessService  = new BusinessService(businessRepo);
//        Assertions.assertThrows(NullPointerException.class,()->businessService.SaveBusinnesToDatabase(business));
//    }

}
