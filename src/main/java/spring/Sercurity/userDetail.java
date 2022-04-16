package spring.Sercurity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.Entity.Model.Role;
import spring.Entity.Model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class userDetail implements UserDetails {
    private String userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetails createUserDetail(User user){
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(role.getNameRole()));
        return new userDetail(user.getUserId(),user.getNameUser(), user.getPassword(), authority);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

