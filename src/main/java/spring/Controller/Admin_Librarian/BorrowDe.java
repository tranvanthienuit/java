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
import spring.Entity.Model.Borrow;
import spring.Entity.BorrowDelist;
import spring.Entity.Model.BorrowDetail;
import spring.Service.BookService;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BorrowDe {
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BookService bookService;

    @DeleteMapping(value = {"/librarian/xoa-borrow-detail/{borrowDeId}", "/librarian/xoa-borrow-detail","/admin/xoa-borrow-detail/{borrowDeId}", "/admin/xoa-borrow-detail"})
    public ResponseEntity<String> removeBorrowDe(@PathVariable(value = "borrowDeId", required = false) String borrowDeId) throws Exception {
        if (borrowDeSevice.findBorrowDe(borrowDeId) != null) {
            BorrowDetail borrowDetail = borrowDeSevice.findBorrowDe(borrowDeId);
            bookService.findBookAndUpdate(borrowDetail.getCount(),borrowDetail.getBook().getBookId());
            borrowDeSevice.removeByBorrowDeId(borrowDeId);
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = {"/librarian/xem-tat-ca-Borrow-Detail/{page}", "/librarian/xem-tat-ca-Borrow-Detail","/admin/xem-tat-ca-Borrow-Detail/{page}", "/admin/xem-tat-ca-Borrow-Detail"})
    public ResponseEntity<BorrowDelist> getAllBorrowDe(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BorrowDelist borrowDelist = new BorrowDelist();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<BorrowDetail> borrowDetailPage = borrowDeSevice.getAllBorrowDe(pageable);
        List<BorrowDetail> borrowDetailPageContent = borrowDetailPage.getContent();
        if (borrowDetailPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            borrowDelist.setBorrowDelists(borrowDetailPageContent);
            borrowDelist.setCount(borrowSevice.getAllBorrow().size());
            return new ResponseEntity<>(borrowDelist, HttpStatus.OK);
        }
    }

    @GetMapping(value = {"/librarian/tim-borrowde/{userName}", "/librarian/tim-borrow","/admin/tim-borrowde/{userName}", "/admin/tim-borrow"})
    private ResponseEntity<List<BorrowDetail>> findBorrowDe(@PathVariable(name = "userName", required = false) String userName) {
        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<BorrowDetail> borrowDetailList = new ArrayList<>();
            List<Borrow> borrow = borrowSevice.findBorrowsByUser(userName);
            for (Borrow borrow1 : borrow) {
                List<BorrowDetail> borrowDetailList1 = borrowDeSevice.findBorrowDetailsByBorrow(borrow1.getBorrowId());
                borrowDetailList.addAll(borrowDetailList1);
            }
            return new ResponseEntity<>(borrowDetailList, HttpStatus.OK);
        }
    }
}
