package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Role;
import spring.Entity.Model.User;
import spring.Service.RoleService;
import spring.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class AdminUser {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @PostMapping("/admin/{userId}")
    public ResponseEntity<?> editeRole(@PathVariable("userId") String userId, @RequestBody Map<String,Object> roleName) {
        User user = userService.findUserByUserId(userId);

        Role role = roleService.fineRoleByName(roleName.get("roleName").toString());
        user.setRole(role);
        userService.saveUser(user);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @GetMapping("/admin/{roleName}")
    public ResponseEntity<?> findUsersByRole(@PathVariable("roleName") String roleName) {
        List<User> userList = userService.findUsersByRole(roleName);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
