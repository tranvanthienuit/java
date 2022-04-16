package spring.Controller.Admin;

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
import spring.Entity.BorrowDelist;
import spring.Entity.Model.BorrowDetail;
import spring.Service.BookService;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.ArrayList;
import java.util.List;


@RestController
public class AdminBorrowDe {
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/admin/xoa-borrow-detail/{borrowDeId}", "/admin/xoa-borrow-detail"})
    public ResponseEntity<BorrowDetail> removeBorrowDe(@PathVariable(value = "borrowDeId", required = false) String borrowDeId) throws Exception {
        if (borrowDeSevice.findBorrowDe(borrowDeId) != null) {
            BorrowDetail borrowDetail = borrowDeSevice.findBorrowDe(borrowDeId);
            bookService.findBookAndUpdate(borrowDetail.getCount(),borrowDetail.getBook().getBookId());
            borrowDeSevice.removeByBorrowDeId(borrowDeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = {"/admin/xem-tat-ca-Borrow-Detail/{page}", "/admin/xem-tat-ca-Borrow-Detail"})
    public ResponseEntity<BorrowDelist> getAllBorrowDe(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BorrowDelist borrowDelist = new BorrowDelist();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<BorrowDetail> borrowDetailPage = borrowDeSevice.getAllBorrowDe(pageable);
        List<BorrowDetail> borrowDetailPageContent = borrowDetailPage.getContent();
        if (borrowDetailPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            borrowDelist.setBorrowDelists(borrowDetailPageContent);
            borrowDelist.setCount(borrowSevice.getAllBorrow().size());
            return new ResponseEntity<>(borrowDelist, HttpStatus.OK);
        }
    }

    @GetMapping(value = {"/admin/tim-borrowde/{userId}", "/admin/tim-borrow"})
    private ResponseEntity<List<BorrowDetail>> findBorrowDe(@PathVariable(name = "userId", required = false) String userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<BorrowDetail> borrowDetailList = new ArrayList<>();
            List<Borrow> borrow = borrowSevice.findBorrowsByUser(userId);
            for (Borrow borrow1 : borrow) {
                List<BorrowDetail> borrowDetailList1 = borrowDeSevice.findBorrowDetailsByBorrow(borrow1.getBorrowId());
                borrowDetailList.addAll(borrowDetailList1);
            }
            return new ResponseEntity<>(borrowDetailList, HttpStatus.OK);
        }
    }
}
