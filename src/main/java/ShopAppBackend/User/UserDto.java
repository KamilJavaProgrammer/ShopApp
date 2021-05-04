package ShopAppBackend.User;
import lombok.Data;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class UserDto {

    public Long id;

    @NotNull(message = "Username can not be null !")
    @NotEmpty(message = "Username can not be Empty !")
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;
    private String password;
    private String email;
    public String changedPassword;
    private ShopClient shopClient;

    private String codeVerification;

}

