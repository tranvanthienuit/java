package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.OrderssDetail;
import spring.Entity.OrderssList;
import spring.Service.BookService;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;

import java.util.List;

@RestController
public class Orderss {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/seller/xem-tat-ca-Orderss/{page}", "/seller/xem-tat-ca-Orderss","/admin/xem-tat-ca-Orderss/{page}", "/admin/xem-tat-ca-Orderss"})
    public ResponseEntity<OrderssList> getAllOrderss(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        OrderssList orderssList = new OrderssList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<spring.Entity.Model.Orderss> OrderssPage = orderssSevice.getAllOrderss(pageable);
        List<spring.Entity.Model.Orderss> orderssPageContent = OrderssPage.getContent();
        if (orderssPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            orderssList.setOrderssList(orderssPageContent);
            orderssList.setCount(orderssSevice.getAllOrderss().size());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"/seller/xoa-Orderss/{OrderssId}", "/seller/xoa-Orderss","/admin/xoa-Orderss/{OrderssId}", "/admin/xoa-Orderss"})
    public ResponseEntity<String> removeOrderss(@PathVariable(value = "OrderssId", required = false) String OrderssId) throws Exception {
        spring.Entity.Model.Orderss orderss = orderssSevice.findOrderssByOrderssId(OrderssId);
        if (orderss != null) {
            List<OrderssDetail> orderssDetails = orderssDeSevice.findOrderssDetailsByOrderss(OrderssId);
            for(OrderssDetail orderssDetail : orderssDetails){
                bookService.findBookAndUpdate(orderssDetail.getCount(), orderssDetail.getBook().getBookId());
            }
            orderssSevice.removeOrderssByOrderssId(orderss.getOrderssId());
            orderssDeSevice.removeByOrderssId(orderss.getOrderssId());
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/seller/tim-Orderss/{userName}", "/seller/tim-Orderss","/admin/tim-Orderss/{userName}", "/admin/tim-Orderss"})
    public ResponseEntity<List<spring.Entity.Model.Orderss>> findOrderss(@PathVariable(name = "userName", required = false) String userName) {
        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<spring.Entity.Model.Orderss> orderssList = orderssSevice.findOrdersssByUser(userName);
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }
}
