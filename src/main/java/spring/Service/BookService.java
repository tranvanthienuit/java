package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Book;
import spring.Repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository booksRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public Book saveBook(Book book) {
        return booksRepository.save(book);
    }

    public List<Book> findBooksByNameBook(String name) {
        return booksRepository.findBooksByNameBook(name);
    }

    public List<Book> findBooksByAuthor(String name) {
        return booksRepository.findBooksByAuthor(name);
    }

    public List<Book> findBooksByCategoryName(String name) {
        return booksRepository.findBooksByCategoryName(name);
    }

    public Book findBooksByBookId(String idBook) {
        return booksRepository.findBooksByBookId(idBook);
    }


    public void removeBookByBookId(String idBook) {
        booksRepository.removeBookByBookId(idBook);
    }

    public Page<Book> getBookByDayAdd(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

}

