package untiled.serwer.serweruntitled.User;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.Client.ShopClient.ShopClient;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Transient
    private String name;
    @Transient
    private String surname;
    private String username;

    private String password;

    private String email;

    private String role;

    private boolean authorization;

    private String codeVerification;

    @Transient
    public String password1;

    @OneToOne
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
