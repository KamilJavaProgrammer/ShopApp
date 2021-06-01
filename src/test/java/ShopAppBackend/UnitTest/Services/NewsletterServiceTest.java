package ShopAppBackend.UnitTest.Services;

import ShopAppBackend.Entities.NewsLetter;
import ShopAppBackend.Repositories.NewsletterRepository;
import ShopAppBackend.Services.NewsLetterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)

public class NewsletterServiceTest {

    @InjectMocks
    NewsLetterService newsLetterService;

    @Mock
    NewsletterRepository newsletterRepository;

    @Before
    public void init() {
        NewsLetter newsLetter1 = new NewsLetter();
        newsLetter1.setId(1);
        newsLetter1.setEmail("example@simple.pl");
        Mockito.when(newsletterRepository.findById(1)).thenReturn(Optional.of(newsLetter1));
    }


    @Test
    @Transactional
    public void DeleteSubscriberById_IdExists_resultEquals(){
        var result = newsLetterService.RemoveObserver(1);
        Assertions.assertEquals(result, HttpStatus.NO_CONTENT);
    }

    @Test
    public void DeleteSubscriberById_IdNoExists_resultEquals(){
        var result = newsLetterService.RemoveObserver(2);
        Assertions.assertEquals(result, HttpStatus.NOT_FOUND);
    }


    @Test
    @Transactional
    public void AttachObserver(){
        String mail = "expample@example.pl";
        var result = newsLetterService.AttachObserver(mail);
        Assertions.assertEquals(result,HttpStatus.CREATED);
    }
}
