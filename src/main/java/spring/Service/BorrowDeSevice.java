package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Book;
import spring.Entity.BookSelect;
import spring.Entity.Model.BorrowDetail;
import spring.Repository.BookRepository;
import spring.Repository.BorrowDeRepository;

import java.util.ArrayList;
import java.util.List;

import static spring.Recommendation.StringSimilarity.similarity;

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


    public List<Book> getBookFromBorrDeAndUser(Pageable pageable, String userId) {
        List<Book> recomBook = new ArrayList<>();
        if (userId.equals("anonymousUser")) {
            userId = " ";
        }
        List<BookSelect> objects = borrowDeRepository.getBookFromBorrDeAndUser(pageable, userId);
        List<Book> bookList = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }

        for (Book book : bookList) {
            for (Book book1 : books) {
                if (similarity(book1.getDescription(), book.getDescription()) > 0.7) {
                    recomBook.add(book);
                }
            }
        }
        return recomBook;
    }

    public List<BorrowDetail> getAllBorrowDe() {
        return borrowDeRepository.findAll();
    }
}
