package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.User;
import spring.Entity.image;
import spring.Sercurity.userDetail;
import spring.Service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/sua-thong-tin")
    public ResponseEntity<User> editInfo(@RequestBody(required = false) User user) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getFullName() != null) {
            userService.editUserFullname(user.getFullName(), userDetail.getUserId());
        }
        if (user.getNameUser() != null) {
            userService.editUserName(user.getNameUser(), userDetail.getUserId());
        }
        if (user.getAddress() != null) {
            userService.editUserAdress(user.getAddress(), userDetail.getUserId());
        }
        if (user.getTelephone() != null) {
            userService.editUserTelephone(user.getTelephone(), userDetail.getUserId());
        }
        if (user.getEmail() != null) {
            userService.editUserEmail(user.getEmail(), userDetail.getUserId());
        }
        if (user.getSex() != null) {
            userService.editUserSex(user.getSex(), userDetail.getUserId());
        }
        user = userService.findUserByUserId(userDetail.getUserId());
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cap-nhat-anh")
    public ResponseEntity<User> editImg(@RequestBody image image) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        user.setImage(image.getImage());
        userService.editImage(getImageByte(), user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/sua-mat-khau/{old-password}/{new-password}")
    public ResponseEntity<?> editPassword(@RequestBody @PathVariable("old-password") String oldPassword, @RequestBody @PathVariable("new-password") String newPassword) {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            userService.editUserPass(passwordEncoder.encode(newPassword), user.getUserId());
            return new ResponseEntity<>("thanh cong roi ban ey", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mat khau cu sai", HttpStatus.OK);
        }
    }
    public byte[] getImageByte(){
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (user.getImage()==null)
            return null;
        return user.getImage().getBytes();
    }
}
