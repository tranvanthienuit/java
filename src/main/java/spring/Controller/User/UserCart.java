package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.*;
import spring.Entity.Model.Book;
import spring.Entity.Model.Borrow;
import spring.Entity.Model.BorrowDetail;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.BookService;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;
import spring.Service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class UserCart {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    UserService userService;

    @GetMapping("/user/muon-sach")
    public ResponseEntity<List<CartBook>> borrow(@RequestBody List<CartBook> cart) throws Exception {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());

        LocalDate ldate = LocalDate.now();
        Date date = Date.valueOf(ldate);
        Date date1 = Date.valueOf(ldate.plusMonths(1));


        Borrow borrow = new Borrow();
        borrow.setBorrowDate(date);
        borrow.setReturnDate(date1);
        borrow.setUser(user);
        borrowSevice.saveBorrow(borrow);


        for (CartBook cartBook : cart) {
            BorrowDetail borrowDetail = new BorrowDetail();
            List<Book> books = bookService.getAllBook();
            for (Book book1 : books) {
                if (cartBook.getBooks().getNameBook().equals(book1.getNameBook())) {
                    if (book1.getCount() - cartBook.getQuantity() > 0) {
                        borrowDetail.setCount(cartBook.getQuantity());
                        bookService.findBookAndUpdate(book1.getCount() - cartBook.getBooks().getCount(), cartBook.getBooks().getBookId());
                    } else {
                        bookService.findBookAndUpdate(0, cartBook.getBooks().getBookId());
                        borrowDetail.setCount(book1.getCount());
                    }
                }
            }
            borrowDetail.setStatus("exist");
            borrowDetail.setBorrow(borrow);
            borrowDetail.setBook(cartBook.getBooks());
            borrowDeSevice.saveBorrowDe(borrowDetail);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
