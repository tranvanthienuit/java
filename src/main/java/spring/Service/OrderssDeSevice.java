package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Book;
import spring.Entity.BookSelect;
import spring.Entity.Model.OrderssDetail;
import spring.Entity.Model.User;
import spring.Entity.book_category;
import spring.Entity.month_price;
import spring.Repository.BookRepository;
import spring.Repository.OrderssDeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class OrderssDeSevice {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderssDeRepository orderssDeRepository;

    public void removeByOrderssDeId(String orderssDeId) {
        OrderssDetail orderssDetail = orderssDeRepository.findOrderssDetailByOrderssDeId(orderssDeId);
        orderssDeRepository.delete(orderssDetail);
    }

    public void saveOrderssDe(OrderssDetail orderssDetail) {
        orderssDeRepository.save(orderssDetail);
    }

    public Page<OrderssDetail> getAllOrderssDe(Pageable pageable) {
        return orderssDeRepository.findAll(pageable);
    }

    public List<OrderssDetail> findOrderssDe(String orderssId) {
        return orderssDeRepository.findAllByOrderssId(orderssId);
    }

    public List<OrderssDetail> findOrderssDetailsByOrderss(String orderssId) {
        return orderssDeRepository.findOrderssDetailsByOrderss(orderssId);
    }

    public List<Book> getBookFromBorrDe(Pageable pageable) {
        List<BookSelect> objects = orderssDeRepository.getBookFromBorrDe(pageable);
        List<Book> bookList = new ArrayList<>();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }
        return bookList;
    }

    public List<OrderssDetail> getAllOrderssDe() {
        return orderssDeRepository.findAll();
    }

    public List<book_category> getBookAndCategory() {
        return orderssDeRepository.getBookAndCategory();
    }

    public List<month_price> getPriceAndMonth() {
        return orderssDeRepository.getPriceAndMonth();
    }
}
