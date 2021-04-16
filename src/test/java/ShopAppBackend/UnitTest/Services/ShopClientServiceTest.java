package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Adress.Address;
import ShopAppBackend.Adress.AddressRepo;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientNotFound;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientRepository;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class ShopClientServiceTest {

    @Mock
    ShopClient shopClient;

    @Mock
    ShopClientRepository shopClientRepository;
    @InjectMocks
    ShopClientService shopClientService;

    @Mock
    AddressRepo addressRepo;
    @Mock
    Address address;


    @Test
    public void DeleteShopClientById_simpleCase_OK(){

        var result = shopClientService.DeleteShopClientById(3L);
        Assertions.assertEquals( result,ResponseEntity.ok(HttpStatus.NO_CONTENT));
    }

    @Test
    public void DeleteShopClientById_idNull_throwsIllegalArgumentException(){
        Long id = null;
        Assertions.assertThrows(IllegalArgumentException.class,()->shopClientService.DeleteShopClientById(id));
    }

    @Test
    public void GetOneShopClientFromDatabase_idsEquals_resultEquals() throws ShopClientNotFound {

        Mockito.when(shopClient.getId()).thenReturn(1L);
        Mockito.when(shopClientRepository.findById(1L)).thenReturn(Optional.of(shopClient));
         var result  = shopClientService.GetOneShopClientFromDatabase(1L);
         Assertions.assertEquals(result.get().getId(),1L);
    }

    @Test
    public void GetOneShopClientFromDatabase_idsEquals_throwsShopClientNotFoundException() throws ShopClientNotFound {

        Mockito.when(shopClient.getId()).thenReturn(1L);
        Mockito.when(shopClientRepository.findById(1L)).thenReturn(Optional.of(shopClient));
        Assertions.assertThrows(ShopClientNotFound.class, () -> shopClientService.GetOneShopClientFromDatabase(2L));
    }

    @Test
    public void GetOneShopClientFromDatabase_idNullthrowsIllegalArgumentException() {

        Mockito.when(shopClient.getId()).thenReturn(1L);
        Mockito.when(shopClientRepository.findById(1L)).thenReturn(Optional.of(shopClient));
        Assertions.assertThrows(IllegalArgumentException.class, () -> shopClientService.GetOneShopClientFromDatabase(null));
    }


    @Test
    public void AddClientToDatabase_valuesOK_ResponseEntity(){
        Mockito.when(shopClient.getId()).thenReturn(1L);
        Mockito.when(shopClient.getName()).thenReturn("John");
        Mockito.when(address.getId()).thenReturn(1L);

        var result = shopClientService.AddClientToDatabase(shopClient);
        Assertions.assertEquals( result,ResponseEntity.ok(HttpStatus.NO_CONTENT));
    }


    @Test
    public void AddClientToDatabase_shopClientNull_throwsIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> shopClientService.AddClientToDatabase(null));
    }

    @Test
    public void GetAllClient_simplecase_ResponseEntityClients(){
        Mockito.when(shopClient.getId()).thenReturn(1L);
        Mockito.when(shopClientRepository.findAll()).thenReturn(Collections.singletonList(shopClient));
        var result = shopClientService.GetAllClient();
        Assertions.assertEquals(result,ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(shopClient)));
    }

}
