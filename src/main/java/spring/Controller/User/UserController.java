package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.User;
import spring.Sercurity.userDetail;
import spring.Service.UserService;
import java.util.Base64;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sua-thong-tin/{userId}")
    public ResponseEntity<?> editInfo(@RequestBody(required = false) User user,@RequestBody @PathVariable(value = "userId",required = false) String userId) throws Exception {
        User user1 = userService.findUserByUserId(userId);
        if (user.getNameUser()!=null) {
            userService.editUserName(user.getNameUser(), user1.getUserId());
        }
        if (user.getPassword()!=null) {
            userService.editUserPass(user.getPassword(), user1.getUserId());
        }
        if (user.getAddress()!=null) {
            userService.editUserAdress(user.getAddress(), user1.getUserId());
        }
        if (user.getTelephone()!=null) {
            userService.editUserTelephone(user.getTelephone(), user1.getUserId());
        }
        if (user.getEmail()!=null) {
            userService.editUserEmail(user.getEmail(), user1.getUserId());
        }
        if (user.getSex()!=null) {
            userService.editUserSex(user.getSex(), user1.getUserId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/cap-nhat-anh/{userId}")
    public ResponseEntity<User> editimg(@RequestBody Base64 image,@RequestBody @PathVariable(value = "userId",required = false) String userId) throws Exception{
        User user = userService.findUserByUserId(userId);
        userService.editImage(image,user.getUserId());
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}
