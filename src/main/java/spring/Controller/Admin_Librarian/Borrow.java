package spring.Controller.Admin_Librarian;

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
import spring.Entity.Model.BorrowDetail;
import spring.Entity.BorrowList;
import spring.Service.BookService;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.List;

@RestController
public class Borrow {
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/librarian/xem-tat-ca-borrow/{page}", "/librarian/xem-tat-ca-borrow","/admin/xem-tat-ca-borrow/{page}", "/admin/xem-tat-ca-borrow"})
    public ResponseEntity<BorrowList> getAllBorrow(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BorrowList borrowList = new BorrowList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<spring.Entity.Model.Borrow> borrowPage = borrowSevice.getAllBorrow(pageable);
        List<spring.Entity.Model.Borrow> borrowPageContent = borrowPage.getContent();
        if (borrowPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            borrowList.setBorrowList(borrowPageContent);
            borrowList.setCount(borrowSevice.getAllBorrow().size());
            return new ResponseEntity<>(borrowList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"/librarian/xoa-borrow/{borrowId}", "/librarian/xoa-borrow","/admin/xoa-borrow/{borrowId}", "/admin/xoa-borrow"})
    public ResponseEntity<String> removeBorrow(@PathVariable(value = "borrowId", required = false) String borrowId) throws Exception {
        spring.Entity.Model.Borrow borrow = borrowSevice.findBorrowByBorrowId(borrowId);
        if (borrow != null) {
            List<BorrowDetail> borrowDetails = borrowDeSevice.findBorrowDetailsByBorrow(borrowId);
            for(BorrowDetail borrowDetail : borrowDetails){
                bookService.findBookAndUpdate(borrowDetail.getCount(),borrowDetail.getBook().getBookId());
            }
            borrowSevice.removeBorrowByBorrowId(borrow.getBorrowId());
            borrowDeSevice.removeByBorrowId(borrow.getBorrowId());
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/librarian/tim-borrow/{userName}", "/librarian/tim-borrow","/admin/tim-borrow/{userName}", "/admin/tim-borrow"})
    public ResponseEntity<List<spring.Entity.Model.Borrow>> findBorrow(@PathVariable(name = "userName", required = false) String userName) {
        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<spring.Entity.Model.Borrow> borrowList = borrowSevice.findBorrowsByUser(userName);
            return new ResponseEntity<>(borrowList, HttpStatus.OK);
        }
    }
}
