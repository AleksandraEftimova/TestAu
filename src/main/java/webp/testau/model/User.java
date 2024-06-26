package webp.testau.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "shop_users")
public class User implements UserDetails {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;

    //default values for security methods,
    //ponatamu koga ke rabotime so niv ke
//    @Column(nullable = false)
    private boolean isAccountNonExpired;
//    @Column(nullable = false)
    private boolean isAccountNonLocked = true;
//    @Column(nullable = false)
    private boolean isCredentialNonExpired = true;
//    @Column(nullable = false)
    private boolean isEnabled = true;

    //ToMany=Lazy, ToOne=Eager by default
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ShoppingCart> carts;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String username, String password, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User() {

    }

    //NAJBITEN, so nego doznava koi ulogi gi ima korisnikot
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //ako e NxN vrskata sepak 1 User can only have 1 Role
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
