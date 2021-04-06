package untiled.serwer.serweruntitled.Product;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;



@RunWith(MockitoJUnitRunner.class)


public class ProductServiceTest {


    @Mock
    Product product;

    @Mock
    ProductRepo productRepo;




    @Test
    public void GetOneProductFromDatabase1_simpleCase_sucess(){

        ProductService productService = new ProductService(productRepo,null,null,null,null,null);
        Mockito.when(product.getId()).thenReturn(1L);
        Mockito.when(productRepo.getOne(1L)).thenReturn(product);


        Product  result  = productService.GetOneProductFromDatabase1(1L);
        Assertions.assertEquals(result.getId(), 1L);

    }


//    @Test
//    public void GetOneProductFromDatabase1_simpleCase_sucess(){
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
