package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Book;
import spring.Service.BookService;
import spring.Service.CategoryService;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService booksService;
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/tim-sach/{infoBook}", "/tim-sach"})
    public ResponseEntity<List<Book>> findBook(@PathVariable(value = "infoBook", required = false) String infoBook) throws Exception {
        if (infoBook != null) {
            if (booksService.findBooksByNameBook(infoBook) != null) {
                List<Book> booksList = booksService.findBooksByNameBook(infoBook);
                return new ResponseEntity<>(booksList, HttpStatus.OK);
            } else {
                if (booksService.findBooksByAuthor(infoBook) != null) {
                    List<Book> booksList = booksService.findBooksByAuthor(infoBook);
                    return new ResponseEntity<>(booksList, HttpStatus.OK);

                } else {
                    if (booksService.findBooksByCategoryName(infoBook) != null) {
                        List<Book> booksList = booksService.findBooksByCategoryName(infoBook);
                        return new ResponseEntity<>(booksList, HttpStatus.OK);

                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = {"/xem-chi-tiet-sach/{bookId}", "/xem-chi-tiet-sach"})
    public ResponseEntity<Book> productdetail(@PathVariable(value = "bookId", required = false) String bookId) throws Exception {
        Book book = booksService.findBooksByBookId(bookId);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sach-moi")
    public ResponseEntity<List<Book>> findbyarrive() throws Exception {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("dayAdd").descending());
        Page<Book> bookPage = booksService.getBookByDayAdd(pageable);
        List<Book> bookList = bookPage.getContent();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<String>> searchByNameBook(@RequestBody @PathVariable("keyword")String keywword){
        List<String> searchString = booksService.searchByNameBook(keywword);
        return new ResponseEntity<>(searchString,HttpStatus.OK);
    }
}
