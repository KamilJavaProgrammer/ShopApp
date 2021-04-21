package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Adress.Address;
import ShopAppBackend.Adress.AddressRepo;
import ShopAppBackend.Adress.AddressService;
import ShopAppBackend.Business.Business;
import ShopAppBackend.Business.BusinessRepo;
import ShopAppBackend.Business.BusinessService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
