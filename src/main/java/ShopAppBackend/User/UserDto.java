package ShopAppBackend.User;
import ShopAppBackend.Message.Message;
import lombok.Data;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
public class UserDto {

    public Long id;

    @NotNull(message = "Username can not be null !")
    @NotEmpty(message = "Username can not be Empty !")
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;
    private String password;
    private String email;
    private String role;
    public String changedPassword;
    private ShopClient shopClient;
    private boolean authorization;
    private String codeVerification;
    private List<Message> messages;


}

