package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Entities.Product;
import ShopAppBackend.Exceptions.ProductNotFound;
import ShopAppBackend.Repositories.ProductRepo;
import ShopAppBackend.Services.ProductService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)


public class ProductServiceTest {



    @Mock
    Product product;

    @Mock
    ProductRepo productRepo;



    @Test
    public void GetOneProductFromDatabase_simpleCase_true() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null);
//        Mockito.when(product.getId()).thenReturn(1L);
        Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result  = productService.GetOneProductFromDatabase(1L);
        Assertions.assertTrue(result.isPresent());
    }


    @Test
    public void GetOneProductFromDatabase_differentId_throwsProductNotFound() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null);
        Mockito.when(product.getId()).thenReturn(2L);
        Mockito.when(productRepo.getOne(3L)).thenReturn(product);
        Assertions.assertThrows(ProductNotFound.class,()->productService.GetOneProductFromDatabase(2L));

    }

    @Test
    public void PatchProductInDatabase_differentId_throwsProductNotFound() throws ProductNotFound {

        ProductService productService = new ProductService(productRepo,null,null,null,null);
        Mockito.when(product.getId()).thenReturn(2L);
        Mockito.when(productRepo.findById(3L)).thenReturn(Optional.of(product));
        Assertions.assertThrows(ProductNotFound.class,()->productService.PatchProductInDatabase(2L,null,null));

    }



}
