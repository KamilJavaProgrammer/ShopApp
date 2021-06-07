package ShopAppBackend.Exceptions;

public class ArticleLineNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "ArticleLine not found";
    public ArticleLineNotFoundException(){
        super(EXCEPTION_MESSAGE);
    }
}
