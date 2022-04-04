package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.BorrowDetail;
import spring.Repository.BorrowDeRepository;

import java.util.List;

@Service
public class BorrowDeSevice {
    @Autowired
    BorrowDeRepository borrowDeRepository;

    public void removeByBorrowId(String idBorrow) {
        borrowDeRepository.removeByBorrowId(idBorrow);
    }

    public void removeByBorrowDeId(String idBorrowDe) {
        borrowDeRepository.removeByBorrowDeId(idBorrowDe);
    }

    public void saveBorrowDe(BorrowDetail borrowDetail) {
        borrowDeRepository.save(borrowDetail);
    }

    public Page<BorrowDetail> getAllBorrowDe(Pageable pageable) {
        return borrowDeRepository.findAll(pageable);
    }

    public BorrowDetail findBorrowDe(String idBorrowDe){
        return borrowDeRepository.findBorrowDetailByBorrowDeId(idBorrowDe);
    }
}
