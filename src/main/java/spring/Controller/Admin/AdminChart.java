package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Chart;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;
import spring.Service.UserService;

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
        Map<Integer,Integer> bookAndMonth = orderssSevice.getBookAndMonth();
        Map<Integer,String> bookAndCategory = orderssDeSevice.getBookAndCategory();
        Map<Integer,Double> priceAndMonth = orderssDeSevice.getPriceAndMonth();
        Map<Integer,Integer> userAndMonth = userService.getUserAndMonnth();
        Chart chart = new Chart(bookAndMonth,bookAndCategory,userAndMonth,priceAndMonth);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}
