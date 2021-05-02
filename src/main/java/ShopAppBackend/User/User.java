package ShopAppBackend.User;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull(message = "Username can not be null !")
    @NotEmpty(message = "Username can not be Empty !")
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;

    @NotNull(message = "Password can not be null !")
    @NotEmpty(message = "Password can not be Empty !")
    @Pattern(regexp = "[a-zA-Z0-9.]*" )
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    private String role;

    private boolean authorization;

    private String codeVerification;

    @Transient
    public String changedPassword;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopClient_id")
    private ShopClient shopClient;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
