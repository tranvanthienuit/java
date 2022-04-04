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
import spring.Entity.BorrowDelist;
import spring.Entity.BorrowDetail;
import spring.Entity.User;
import spring.Service.BorrowDeSevice;

import java.util.List;


@RestController
public class AdminBorrowDe {
    @Autowired
    BorrowDeSevice borrowDeSevice;

    @GetMapping(value = {"/admin/xoa-borrow-detail/{idBorrowDe}","/admin/xoa-borrow-detail"})
    public ResponseEntity<BorrowDetail> removeBorrowDe(@PathVariable(value = "idBorrowDe", required = false) String idBorrowDe) throws Exception {
        if (borrowDeSevice.findBorrowDe(idBorrowDe) != null) {
            borrowDeSevice.removeByBorrowDeId(idBorrowDe);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = {"/admin/xem-tat-ca-Borrow-Detail/{page}","/admin/xem-tat-ca-Borrow-Detail"})
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
            borrowDelist.setCount(borrowDetailPageContent.size());
            return new ResponseEntity<>(borrowDelist, HttpStatus.OK);
        }
    }
}
