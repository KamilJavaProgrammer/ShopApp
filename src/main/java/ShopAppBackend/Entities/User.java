package ShopAppBackend.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

//    @NotNull(message = "Username can not be null !")
//    @NotEmpty(message = "Username can not be Empty !")
//    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;

//    @NotNull(message = "Password can not be null !")
//    @NotEmpty(message = "Password can not be Empty !")
    private String password;


    @Email
    private String email;

    private String role;

    private boolean authorization;

    private String codeVerification;

    @Transient
    public String changedPassword;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopClient_id")
    private ShopClient shopClient;



    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    private List<Message> messages;



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", authorization=" + authorization +
                ", codeVerification='" + codeVerification + '\'' +
                ", changedPassword='" + changedPassword + '\'' +
                ", shopClient=" + shopClient +
                ", messages=" + messages +
                '}';
    }



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
       return authorization;
    }

}
