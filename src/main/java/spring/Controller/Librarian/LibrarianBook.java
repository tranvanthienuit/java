package spring.Controller.Librarian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.Book;
import spring.Entity.BookList;
import spring.Service.BookService;
import spring.Service.CategoryService;

import java.util.List;

@RestController
public class LibrarianBook {
    @Autowired
    BookService booksService;
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/librarian/xem-tat-ca-sach/{page}", "/librarian/xem-tat-ca-sach"})
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
}
