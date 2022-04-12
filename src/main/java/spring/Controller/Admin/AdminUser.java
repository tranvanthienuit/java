package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.User;
import spring.Entity.UserList;
import spring.Service.RoleService;
import spring.Service.UserService;
import java.util.List;

@RestController
public class AdminUser {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @GetMapping(value = {"/admin/xoa-user/{idUser}","/admin/xoa-user"})
    public ResponseEntity<User> removeUser(@PathVariable(value = "idUser",required = false) String idUser) throws Exception {
        if (userService.findUserByUserId(idUser)!=null){
            if (!userService.countUser().equals(1)) {
                userService.removeUserByUserId(idUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-user/{page}","/admin/xem-tat-ca-user"})
    public ResponseEntity<UserList> getAllUser(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        UserList userList = new UserList();
        if (page==null){
            page=0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<User> userPage = userService.getAllUser(pageable);
        List<User> userPageContent = userPage.getContent();
        if (userPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userList.setUserList(userPageContent);
            userList.setCount(userPageContent.size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

}
