package spring.Controller.Admin;

import com.sun.mail.imap.protocol.BODY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Book;
import spring.Entity.BookList;
import spring.Service.BookService;
import spring.Service.CategoryService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AdminBook {
    @Autowired
    BookService booksService;
    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "/admin/luu-sach")
    public ResponseEntity<String> saveBook(@RequestBody Book book) throws Exception {
        List<Book> books = booksService.getAllBook();
        for (Book book1 : books) {
            if (book.getNameBook().equals(book1.getNameBook()) && book.getBookId().equals(book1.getBookId())) {
                booksService.findBookAndUpdate(book1.getCount() + book.getCount(), book.getBookId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        book.setDayAdd(date);
        booksService.saveBook(book);
        return new ResponseEntity<>("successful",HttpStatus.OK);
    }

    @DeleteMapping(value = {"/admin/xoa-sach/{bookId}", "/admin/xoa-sach"})
    public ResponseEntity<String> removeBook(@PathVariable(value = "bookId", required = false) String bookId) throws Exception {
        if (booksService.findBookByBookId(bookId) != null) {
            booksService.removeBookByBookId(bookId);
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = {"/admin/sua-sach/{idBook}"})
    public ResponseEntity<Book> editBook(@RequestBody Book book){
        Book newBook = booksService.findBookByBookId(book.getBookId());
        if (book.getNameBook()!=null){
            newBook.setNameBook(book.getNameBook());
            booksService.saveBook(newBook);
        }
        if (book.getAuthor()!=null){
            newBook.setAuthor(book.getAuthor());
            booksService.saveBook(newBook);
        }
        if (book.getPublishYear()!=null){
            newBook.setPublishYear(book.getPublishYear());
            booksService.saveBook(newBook);
        }
        if (book.getPublishCom()!=null){
            newBook.setPublishCom(book.getPublishCom());
            booksService.saveBook(newBook);
        }
        if (book.getPrice()!=null){
            newBook.setPrice(book.getPrice());
            booksService.saveBook(newBook);
        }
        if (book.getCount()!=null){
            newBook.setCount(book.getCount());
            booksService.saveBook(newBook);
        }
        if (book.getDescription()!=null){
            newBook.setDescription(book.getDescription());
            booksService.saveBook(newBook);
        }
        if (book.getImage()!=null){
            newBook.setImage(book.getImage());
            booksService.saveBook(newBook);
        }
        if (book.getCategory()!=null){
            newBook.setCategory(book.getCategory());
            booksService.saveBook(newBook);
        }
        Book bookByName = booksService.findBookByBookId(book.getBookId());
        if (bookByName!=null){
            return new ResponseEntity<>(bookByName,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
