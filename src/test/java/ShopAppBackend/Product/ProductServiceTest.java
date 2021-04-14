package ShopAppBackend.Product;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)


public class ProductServiceTest {



    @Mock
    Product product;

    @Mock
    ProductRepo productRepo;



    @Test
    public void GetOneProductFromDatabase_simpleCase_true() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null,null);
//        Mockito.when(product.getId()).thenReturn(1L);
        Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result  = productService.GetOneProductFromDatabase(1L);
        Assertions.assertTrue(result.isPresent());
    }


    @Test
    public void GetOneProductFromDatabase_differentId_throwsProductNotFound() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null,null);
        Mockito.when(product.getId()).thenReturn(2L);
        Mockito.when(productRepo.getOne(3L)).thenReturn(product);
        Assertions.assertThrows(ProductNotFound.class,()->productService.GetOneProductFromDatabase(2L));

    }

    @Test
    public void PatchProductInDatabase_differentId_throwsProductNotFound() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null,null);
        Mockito.when(product.getId()).thenReturn(2L);
        Mockito.when(productRepo.findById(3L)).thenReturn(Optional.of(product));
        Assertions.assertThrows(ProductNotFound.class,()->productService.PatchProductInDatabase(2L,null,null));

    }















//    @Test
//    public void GetOneProductFromDatabase1_simpleCase_sucess1(){
//
//        //given
//
//        ProductRepo productRepo = createMock();
//        ProductService productService = new ProductService(productRepo, null, null, null,
//                null, null);
//
//        //when
//
//        Product  result  = productService.GetOneProductFromDatabase1(1L);
//
//        //then
//        Assertions.assertEquals(result.getId(), 1L);
//
//    }
//
//
//    public Product dbcontent(){
//        return createMockproduct();
//    }
//
//
//    public ProductRepo createMock(){
//
//        ProductRepo mock = Mockito.mock(ProductRepo.class);
//        Product dupaczka = dbcontent();
//        Mockito.when(mock.getOne(1L)).thenReturn(dupaczka);
//        return mock;
//
//    }
//
//    public Product createMockproduct(){
//
//        Product productMock = Mockito.mock(Product.class);
//        Mockito.when(productMock.getId()).thenReturn(1L);
//
//        return productMock;
//
//    }
}
