package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Entities.Address;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.AddressRepo;
import ShopAppBackend.Services.AddressService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

    @Mock
    Address address;
    @Mock
    AddressRepo addressRepo;

    @Mock
    LogsApplication logsApplication;




    @Test
    public void SaveAddressToDatabase1_businessNull_throwsNullPointerException(){
        AddressService addressService = new AddressService(addressRepo,null);
        Assertions.assertThrows(NullPointerException.class,()->addressService.SaveAddressToDatabase(address));
    }


}
