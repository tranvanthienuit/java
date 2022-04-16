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
import spring.Entity.Model.Borrow;
import spring.Entity.Model.BorrowDetail;
import spring.Entity.BorrowList;
import spring.Service.BookService;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.List;

@RestController
public class LibrarianBorrow {
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/librarian/xem-tat-ca-borrow/{page}", "/librarian/xem-tat-ca-borrow"})
    public ResponseEntity<BorrowList> getAllBorrow(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BorrowList borrowList = new BorrowList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<Borrow> borrowPage = borrowSevice.getAllBorrow(pageable);
        List<Borrow> borrowPageContent = borrowPage.getContent();
        if (borrowPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            borrowList.setBorrowList(borrowPageContent);
            borrowList.setCount(borrowSevice.getAllBorrow().size());
            return new ResponseEntity<>(borrowList, HttpStatus.OK);
        }
    }

    @GetMapping(value = {"/librarian/xoa-borrow/{borrowId}", "/librarian/xoa-borrow"})
    public ResponseEntity<Borrow> removeBorrow(@PathVariable(value = "borrowId", required = false) String borrowId) throws Exception {
        Borrow borrow = borrowSevice.findBorrowByBorrowId(borrowId);
        if (borrow != null) {
            List<BorrowDetail> borrowDetails = borrowDeSevice.findBorrowDetailsByBorrow(borrowId);
            for(BorrowDetail borrowDetail : borrowDetails){
                bookService.findBookAndUpdate(borrowDetail.getCount(),borrowDetail.getBook().getBookId());
            }
            borrowSevice.removeBorrowByBorrowId(borrow.getBorrowId());
            borrowDeSevice.removeByBorrowId(borrow.getBorrowId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"/librarian/tim-borrow/{userId}", "/librarian   /tim-borrow"})
    public ResponseEntity<List<Borrow>> findBorrow(@PathVariable(name = "userId", required = false) String userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Borrow> borrowList = borrowSevice.findBorrowsByUser(userId);
            return new ResponseEntity<>(borrowList, HttpStatus.OK);
        }
    }
}
