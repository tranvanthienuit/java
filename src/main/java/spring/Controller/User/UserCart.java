package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.*;
import spring.Entity.Model.Book;
import spring.Entity.Model.Orderss;
import spring.Entity.Model.OrderssDetail;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.BookService;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;
import spring.Service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class UserCart {
    @Autowired
    BookService bookService;
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;

    @PostMapping("/user/mua-sach")
    public ResponseEntity<List<CartBook>> Orderss(@RequestBody List<CartBook> cart) throws Exception {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());

        LocalDate ldate = LocalDate.now();
        Date date = Date.valueOf(ldate);


        Orderss orderss = new Orderss();
        orderss.setOrderssDate(date);
        orderss.setUser(user);
        Integer totalBook = 0;


        for (CartBook cartBook : cart) {
            totalBook = totalBook+cartBook.getQuantity();
            OrderssDetail orderssDetail = new OrderssDetail();
            for (Book book1 : bookService.getAllBook()) {
                // tìm từng cuốn sách
                Book book = bookService.findBookByBookId(cartBook.getBooks());
                //so sánh có trong list sach không
                if (book.getBookId().equals(book1.getBookId())) {
                    //nếu sách nhiều hơn mua thì lấy sách trừ mua
                    if (book1.getCount() - cartBook.getQuantity() >= 0) {
                        orderssDetail.setCount(cartBook.getQuantity());
                        orderssDetail.setTotal((Double) cartBook.getTotal());
                        bookService.findBookAndUpdate(book1.getCount() - cartBook.getQuantity(), book.getBookId());
                    } else {
                        bookService.findBookAndUpdate(0, book.getBookId());
                        orderssDetail.setTotal((Double) cartBook.getTotal());
                        orderssDetail.setCount(book1.getCount());
                    }
                }
            }
            orderss.setTotalBook(totalBook);
            orderssSevice.saveOrderss(orderss);


            orderssDetail.setStatus("exist");
            orderssDetail.setOrderss(orderss);
            Book book = bookService.findBookByBookId(cartBook.getBooks());
            orderssDetail.setBook(book);
            orderssDeSevice.saveOrderssDe(orderssDetail);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
