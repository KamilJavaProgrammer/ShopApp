package ShopAppBackend.User;
import ShopAppBackend.Logs.LogsApplication;
import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;


public class UserNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "User not found";
    public UserNotFoundException(){
        super(EXCEPTION_MESSAGE);
    }

}
