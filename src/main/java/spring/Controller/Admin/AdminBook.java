package spring.Controller.Admin;

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

    @GetMapping(value = {"/admin/xem-tat-ca-sach/{page}", "/admin/xem-tat-ca-sach"})
    public ResponseEntity<BookList> getAllBook(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 8);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        if (bookPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookList.setBookList(bookPageContent);
            bookList.setCount(booksService.getAllBook().size());
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/admin/luu-sach")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) throws Exception {
        List<Book> books = booksService.getAllBook();
        for (Book book1 : books) {
            if (book.getNameBook().equals(book1.getNameBook())) {
                booksService.findBookAndUpdate(book1.getCount() + book.getCount(), book.getBookId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        book.setDayAdd(date);
        booksService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/xoa-sach/{bookId}", "/admin/xoa-sach"})
    public ResponseEntity<Book> removeBook(@PathVariable(value = "bookId", required = false) String bookId) throws Exception {
        if (booksService.findBooksByBookId(bookId) != null) {
            booksService.removeBookByBookId(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
