package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sua-thong-tin")
    public ResponseEntity<User> editInfo(@RequestBody(required = false) User user) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getFullName()!=null){
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
        if (user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cap-nhat-anh")
    public ResponseEntity<User> editImg(@RequestBody String image) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        byte[] newImage = image.getBytes();
        userService.editImage(newImage, user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/sua-mat-khau/{old-password}/{new-password}")
    public ResponseEntity<?> editPassword(@RequestBody @PathVariable("old-password") String oldPassword,@RequestBody @PathVariable("new-password") String newPassword){
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (!oldPassword.equals(user.getPassword())){
            return new ResponseEntity<>("mat khau cu sai",HttpStatus.BAD_REQUEST);}
        else {
            userService.editUserPass(newPassword,user.getUserId());
            return new ResponseEntity<>("thanh cong roi ban ey",HttpStatus.OK);
        }
    }
}
