package spring.Entity;

import lombok.Data;
import spring.Entity.Model.User;

import java.util.List;

@Data
public class Cart {
    private List<CartBook> cartBooks;
    private User user;
}
