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
import spring.Entity.Borrow;
import spring.Entity.BorrowDetail;
import spring.Entity.BorrowList;
import spring.Service.BorrowDeSevice;
import spring.Service.BorrowSevice;

import java.util.List;


@RestController
public class AdminBorrow {
    @Autowired
    BorrowSevice borrowSevice;
    @Autowired
    BorrowDeSevice borrowDeSevice;

    @GetMapping(value = {"/admin/xoa-borrow/{idBorrow}","/admin/xoa-borrow"})
    public ResponseEntity<Borrow> removeBorrow(@PathVariable(value = "idBorrow", required = false) String idBorrow) throws Exception {
        Borrow borrow = borrowSevice.findBorrowByBorrowId(idBorrow);
        if (borrow != null) {
            borrowSevice.removeBorrowByBorrowId(borrow.getBorrowId());
            borrowDeSevice.removeByBorrowId(borrow.getBorrowId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-borrow/{page}","/admin/xem-tat-ca-borrow"})
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
        } else{
            borrowList.setBorrowList(borrowPageContent);
            borrowList.setCount(borrowPageContent.size());
            return new ResponseEntity<>(borrowList, HttpStatus.OK);
        }
    }
}
