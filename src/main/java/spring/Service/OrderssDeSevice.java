package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Book;
import spring.Entity.BookSelect;
import spring.Entity.Model.OrderssDetail;
import spring.Entity.Model.User;
import spring.Repository.BookRepository;
import spring.Repository.OrderssDeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static spring.Recommendation.StringSimilarity.similarity;

@Service
public class OrderssDeSevice {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderssDeRepository orderssDeRepository;

    public void removeByOrderssId(String idOrderss) {
        orderssDeRepository.removeByOrderssId(idOrderss);
    }

    public void removeByOrderssDeId(String idOrderssDe) {
        orderssDeRepository.removeByOrderssDeId(idOrderssDe);
    }

    public void saveOrderssDe(OrderssDetail orderssDetail) {
        orderssDeRepository.save(orderssDetail);
    }

    public Page<OrderssDetail> getAllOrderssDe(Pageable pageable) {
        return orderssDeRepository.findAll(pageable);
    }

    public OrderssDetail findOrderssDe(String idOrderssDe) {
        return orderssDeRepository.findOrderssDetailByOrderssDeId(idOrderssDe);
    }

    public List<OrderssDetail> findOrderssDetailsByOrderss(String OrderssId) {
        return orderssDeRepository.findOrderssDetailsByOrderss(OrderssId);
    }

    public List<Book> getBookFromBorrDe(Pageable pageable) {
        List<BookSelect> objects = orderssDeRepository.getBookFromBorrDe(pageable);
        List<Book> bookList = new ArrayList<>();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }
        return bookList;
    }


    public List<Book> getBookFromBorrDeAndUser(Pageable pageable, User user) {
        List<Book> recomBook = new ArrayList<>();
        if (user != null) {
            List<BookSelect> objects = orderssDeRepository.getBookFromBorrDeAndUser(pageable, user.getUserId());
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
        recomBook = Collections.emptyList();
        return recomBook;
    }

    public List<OrderssDetail> getAllOrderssDe() {
        return orderssDeRepository.findAll();
    }

    public Map<Integer, String> getBookAndCategory() {
        return orderssDeRepository.getBookAndCategory();
    }

    public Map<Integer, Double> getPriceAndMonth() {
        return orderssDeRepository.getPriceAndMonth();
    }
}
