package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Entity.Model.Book;
import spring.Repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository booksRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return booksRepository.getAllBooks(pageable);
    }
    public Page<Book> getAllBook(Pageable pageable) {
        return booksRepository.getAllBook(pageable);
    }

    public Book saveBook(Book book) {
        return booksRepository.save(book);
    }

    public List<Book> searchBook(String keyword){
        return booksRepository.searchBook(keyword);
    }

    public List<Book> findBooksByCategoryId(String categoryId,Pageable pageable){
        return booksRepository.findBooksByCategoryId(categoryId,pageable);
    }

    public Book findBooksByBookId(String idBook) {
        return booksRepository.findBooksByBookId(idBook);
    }


    public void removeBookByBookId(String idBook) {
        Book book = booksRepository.findBooksByBookId(idBook);
        booksRepository.delete(book);
    }

    public Page<Book> getBookByDayAdd(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public List<Book> getAllBook() {
        return booksRepository.findAll();
    }

    public void findBookAndUpdate(Integer count, String bookId) {
        booksRepository.findBookAndUpdate(count, bookId);
    }

    public Book findBookByBookId(String IdBook) {
        return booksRepository.findBooksByBookId(IdBook);
    }

    public List<String> searchAuto(String keyword){
        return booksRepository.searchAuto(keyword);
    }


    public List<Book> findBookByCondition(String tacgia,Integer giathap,Integer giacao,Integer namsb,Pageable pageable){
        return booksRepository.findBookByCondition(tacgia, giathap, giacao, namsb,pageable);
    }

    public List<Book> getBookByRating(Pageable pageable){
        return booksRepository.getBookByRating(pageable);
    }

    public List<Book> findBooksByCategoryId(String categoryId){
        return booksRepository.findBooksByCategoryId(categoryId);
    }
}

