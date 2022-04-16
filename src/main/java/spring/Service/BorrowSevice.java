package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Borrow;
import spring.Entity.Model.User;
import spring.Repository.BorrowRepository;

import java.util.Date;
import java.util.List;

@Service
public class BorrowSevice {
    @Autowired
    BorrowRepository borrowRepository;

    public Borrow findBorrowByBorrowId(String idBorrow) {
        return borrowRepository.findBorrowByBorrowId(idBorrow);
    }

    public void removeBorrowByBorrowId(String idBorrow) {
        borrowRepository.removeBorrowByBorrowId(idBorrow);
    }


    public void saveBorrow(Borrow borrow) {
        borrowRepository.save(borrow);
    }

    public Borrow findBorrowByBorrowDateAndUserId(Date date, User user) {
        return borrowRepository.findBorrowByBorrowDateAndUserId(date, user);
    }

    public Page<Borrow> getAllBorrow(Pageable pageable) {
        return borrowRepository.findAll(pageable);
    }

    public List<Borrow> findBorrowsByUser(String userId) {
        return borrowRepository.findBorrowsByUser(userId);
    }

    public List<Borrow> getAllBorrow() {
        return borrowRepository.findAll();
    }
}
