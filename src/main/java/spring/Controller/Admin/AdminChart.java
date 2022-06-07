package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.*;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;
import spring.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class AdminChart {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;
    @GetMapping(value = "/admin/chart")
    public ResponseEntity<?> chart(){
        List<month_book> bookAndMonth = orderssSevice.getBookAndMonth();
        if (orderssSevice.getBookAndMonth() == null || orderssSevice.getBookAndMonth().isEmpty()){
            bookAndMonth.clear();
        }
        List<book_category> bookAndCategory = orderssDeSevice.getBookAndCategory();
        if (bookAndCategory == null || bookAndCategory.isEmpty()){
            bookAndCategory.clear();
        }
        List<month_price> priceAndMonth = orderssDeSevice.getPriceAndMonth();
        if (priceAndMonth == null || priceAndMonth.isEmpty()){
            priceAndMonth.clear();
        }
        List<month_user> userAndMonth = userService.getUserAndMonnth();
        if (userAndMonth == null || userAndMonth.isEmpty()){
            userAndMonth.clear();
        }
        Chart chart = new Chart(bookAndMonth,bookAndCategory,userAndMonth,priceAndMonth);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}
