package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Mail;
import spring.Entity.Model.User;
import spring.Entity.image;
import spring.Repository.MailService;
import spring.Sercurity.userDetail;
import spring.Service.UserService;

import java.util.Random;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;

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
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            userService.editUserPass(passwordEncoder.encode(newPassword), user.getUserId());
            return new ResponseEntity<>("thanh cong roi ban ey", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mat khau cu sai", HttpStatus.OK);
        }
    }

    public byte[] getImageByte() {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (user.getImage() == null)
            return null;
        return user.getImage().getBytes();
    }

    @PostMapping("/quen-mat-khau/{email}")
    public ResponseEntity<?> forgetPass(@PathVariable("email") String email) {
        if (mailService.checkMail(email)) {
            Mail mail = new Mail();
            mail.setMailFrom("uitsneaker@gmail.com");
            mail.setMailTo(email);
            mail.setMailSubject("Quên password");
            mail.setMailContent("<h1>Reset Password</h1></br></br>\n" +
                    "<h2>Xin chào quý khách mật khẩu của bạn đang được reset.</br>\n" +
                    "\tHãy nhấp vào link dưới đây để cài đặt mật khẩu lại. Cảm ơn quý khách\n</h2>\n" +
                    "<h3>Link: </h3>" + "<a href="+"https://cai-dat-mat-khau-moi/"+">"+email+"</a>");
            mailService.sendEmail(mail);
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("không có mail nào trong tài khoản", HttpStatus.OK);
    }
    @PostMapping("/cai-dat-mat-khau-moi/{email}/{password}")
    public ResponseEntity<?> setPassword(@PathVariable("email")String email,@PathVariable("password")String password){
        userService.setPassword(passwordEncoder.encode(password),email);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
