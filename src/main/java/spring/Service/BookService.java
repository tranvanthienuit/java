package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Entity.BookList;
import spring.Entity.DataFilter;
import spring.Entity.Filter;
import spring.Entity.Model.Book;
import spring.Repository.BookRepository;

import java.security.PublicKey;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository booksRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return booksRepository.getAllBooks(pageable);
    }

    public Page<Book> getAllBookByAdmin(Pageable pageable) {
        return booksRepository.getAllBook(pageable);
    }

    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    public List<Book> searchBook(String keyword) {
        return booksRepository.searchBook(keyword);
    }

    public List<Book> findBooksByCategoryId(String categoryId, Pageable pageable) {
        return booksRepository.findBooksByCategoryId(categoryId, pageable);
    }

    public void removeBookByBookId(String idBook) {
        Book book = booksRepository.findBooksByBookId(idBook);
        booksRepository.delete(book);
    }

    public Page<Book> getBookByDayAdd(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public List<Book> getAllBook() {
        return booksRepository.countBook();
    }

    public void findBookAndUpdate(Integer count, String bookId) {
        booksRepository.findBookAndUpdate(count, bookId);
    }

    public Book findBookByBookId(String bookId) {
        return booksRepository.findBooksByBookId(bookId);
    }

    public List<String> searchAuto(String keyword) {
        return booksRepository.searchAuto(keyword);
    }


    public BookList findBookByCondition(Filter filter, Pageable pageable) {
        BookList bookList = new BookList();
        if (filter.getGiathap() == null)
            filter.setGiathap(0);
        if (filter.getGiacao() == null)
            filter.setGiacao(booksRepository.maxPrice());
        bookList.setBookList(booksRepository.findBookByCondition(filter.getTacgia(), filter.getGiathap(), filter.getGiacao(), filter.getNamsb(), filter.getLoai(), pageable));
        bookList.setCount(booksRepository.findBookByCondition(filter.getTacgia(), filter.getGiathap(), filter.getGiacao(), filter.getNamsb(), filter.getLoai()));
        return bookList;
    }

    public List<Book> getBookByRating(Pageable pageable) {
        return booksRepository.getBookByRating(pageable);
    }

    public List<Book> findBooksByCategoryId(String categoryId) {
        return booksRepository.findBooksByCategoryId(categoryId);
    }

    public DataFilter dataFilters() {
        DataFilter dataFilter = new DataFilter();
        dataFilter.setTacgia(booksRepository.findAuthor());
        dataFilter.setGia(booksRepository.findPrice());
        dataFilter.setNamsb(booksRepository.findYearPublic());
        return dataFilter;
    }
}

