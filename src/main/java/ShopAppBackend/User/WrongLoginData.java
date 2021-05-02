package ShopAppBackend.User;


import liquibase.pro.license.keymgr.c;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Wrong Login or Password")
public class WrongLoginData extends RuntimeException {

    public WrongLoginData(){
        super();
    }
}
