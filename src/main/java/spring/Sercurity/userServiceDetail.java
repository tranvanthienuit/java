package spring.Sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.Entity.User;
import spring.Service.UserService;

@Service
public class userServiceDetail implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDetail.createUserDetail(user);
    }

}