package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    List<User> userList;
    int count;
}
