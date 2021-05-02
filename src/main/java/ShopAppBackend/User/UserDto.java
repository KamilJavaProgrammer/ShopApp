package ShopAppBackend.User;
import lombok.Data;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Data
public class UserDto {

    public Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean authorization;
    private String codeVerification;
    public String changedPassword;
    private ShopClient shopClient;
}
