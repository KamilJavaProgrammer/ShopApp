package ShopAppBackend.Exceptions;

public class SectionNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Section not found";
    public SectionNotFoundException(){
        super(EXCEPTION_MESSAGE);
    }
}
