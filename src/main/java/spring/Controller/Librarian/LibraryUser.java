package spring.Controller.Librarian;

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
public class LibraryUser {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @GetMapping(value = {"/librarian/xoa-user/{userId}", "/librarian/xoa-user"})
    public ResponseEntity<User> removeUser(@PathVariable(value = "userId", required = false) String userId) throws Exception {
        if (userService.findUserByUserId(userId) != null) {
            if (!userService.countUser().equals(1)) {
                userService.removeUserByUserId(userId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"/librarian/xem-tat-ca-user/{page}", "/librarian/xem-tat-ca-user"})
    public ResponseEntity<UserList> getAllUser(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        UserList userList = new UserList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<User> userPage = userService.getAllUser(pageable);
        List<User> userPageContent = userPage.getContent();
        if (userPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userList.setUserList(userPageContent);
            userList.setCount(userService.getAllUsers().size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }
}
