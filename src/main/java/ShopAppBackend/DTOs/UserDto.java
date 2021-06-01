package ShopAppBackend.DTOs;
import ShopAppBackend.Entities.Message;
import lombok.Data;
import ShopAppBackend.Entities.ShopClient;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;


@Data
public class UserDto  implements Serializable {

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


    public UserDto() {
    }
}

