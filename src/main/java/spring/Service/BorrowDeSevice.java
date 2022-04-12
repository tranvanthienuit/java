package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Book;
import spring.Entity.BorrowDetail;
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

    public List<BorrowDetail> findBorrowDetailsByBorrow(String borrowId){
        return borrowDeRepository.findBorrowDetailsByBorrow(borrowId);
    }

    public Page<Book> getBookFromBorrDe(Pageable pageable) {
        return borrowDeRepository.getBookFromBorrDe(pageable);
    }


    public List<Book> getBookFromBorrDeAndUser(Pageable pageable, String userId) {
        List<Book> recomBook = new ArrayList<>();
        if (userId.equals("anonymousUser")){
            userId=" ";
        }
        Page<Book> bookPage = borrowDeRepository.getBookFromBorrDeAndUser(pageable, userId);
        List<Book> bookList = bookPage.getContent();
        List<Book> books = bookRepository.findAll();
        for (Book book:bookList){
            for (Book book1:books){
                if (similarity(book1.getDescription(),book.getDescription())>0.7){
                    recomBook.add(book);
                }
            }
        }
        return recomBook;
    }
}
