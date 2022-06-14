package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Book;
import spring.Entity.BookSelect;
import spring.Entity.Model.BorrowDetail;
import spring.Entity.Model.User;
import spring.Repository.BookRepository;
import spring.Repository.BorrowDeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class BorrowDeSevice {
    @Autowired
    BookRepository bookRepository;
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

    public BorrowDetail findBorrowDe(String idBorrowDe) {
        return borrowDeRepository.findBorrowDetailByBorrowDeId(idBorrowDe);
    }

    public List<BorrowDetail> findBorrowDetailsByBorrow(String borrowId) {
        return borrowDeRepository.findBorrowDetailsByBorrow(borrowId);
    }

    public List<Book> getBookFromBorrDe(Pageable pageable) {
        List<BookSelect> objects = borrowDeRepository.getBookFromBorrDe(pageable);
        List<Book> bookList = new ArrayList<>();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }
        return bookList;
    }



    public List<BorrowDetail> getAllBorrowDe() {
        return borrowDeRepository.findAll();
    }
}
