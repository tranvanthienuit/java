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
import spring.Entity.Borrow;
import spring.Entity.BorrowDelist;
import spring.Entity.BorrowDetail;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LibrarianBorrowDe {
    @Autowired
    BorrowDeSevice borrowDeSevice;
    @Autowired
    BorrowSevice borrowSevice;

    @GetMapping(value = {"/librarian/xoa-borrow-detail/{idBorrowDe}", "/librarian/xoa-borrow-detail"})
    public ResponseEntity<BorrowDetail> removeBorrowDe(@PathVariable(value = "idBorrowDe", required = false) String idBorrowDe) throws Exception {
        if (borrowDeSevice.findBorrowDe(idBorrowDe) != null) {
            borrowDeSevice.removeByBorrowDeId(idBorrowDe);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = {"/librarian/xem-tat-ca-Borrow-Detail/{page}", "/librarian/xem-tat-ca-Borrow-Detail"})
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

    @GetMapping(value = {"/librarian/tim-borrowde/{userId}", "/librarian/tim-borrow"})
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
