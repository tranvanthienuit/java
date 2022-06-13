package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.BookList;
import spring.Entity.Model.Book;
import spring.Entity.Model.Rating;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.BookService;
import spring.Service.CategoryService;
import spring.Service.RatingService;
import spring.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    BookService booksService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    RatingService ratingService;

    @PostMapping(value = {"/tim-sach"})
    public ResponseEntity<List<Book>> findBook(@RequestBody Map<String, Object> infoBook) throws Exception {
        List<Book> books = booksService.searchBook(infoBook.get("infoBook").toString());
        if (infoBook.get("infoBook").toString() != null) {
            if (books.size() != 0) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
        Pageable pageable = PageRequest.of(0, 6, Sort.by("dayAdd").descending());
        Page<Book> bookPage = booksService.getBookByDayAdd(pageable);
        List<Book> bookList = bookPage.getContent();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping(value = {"/search/book"})
    public ResponseEntity<List<String>> searchByNameBook(@RequestBody Map<String,String> keywword) {
        List<String> searchString = booksService.searchAuto(keywword.get("keyword"));
        return new ResponseEntity<>(searchString, HttpStatus.OK);
    }

    @GetMapping(value = {"/search/{categoryId}/{page}", "/search/{page}"})
    public ResponseEntity<?> findBookByCategory(@PathVariable(name = "page", required = false) Integer page, @PathVariable(value = "categoryId", required = false) String categoryId) {
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 8);
        List<Book> book = booksService.findBooksByCategoryId(categoryId, pageable);
        BookList bookList = new BookList();
        bookList.setBookList(book);
        bookList.setCount(booksService.findBooksByCategoryId(categoryId).size());
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> findBookByCondition(@RequestBody Map<String,Object> keyword,
                                                 @RequestParam(name = "page", required = false) Integer page) {
        if (page == null)
            page = 0;
        Pageable pageable = PageRequest.of(page, 1);
        List<Book> bookList = booksService.findBookByCondition(keyword.get("tacgia").toString(), (Integer) keyword.get("giathap"),
                (Integer) keyword.get("tacgia"), (Integer) keyword.get("namsb"), pageable);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping(value = {"/danh-gia-sach/{bookId}/{star}", "/danh-gia-sach"})
    public ResponseEntity<?> appriciateBook(@PathVariable(value = "bookId", required = false) String bookId, @PathVariable(value = "star", required = false) int star) {
        Rating rating = new Rating();
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        Book book = booksService.findBooksByBookId(bookId);
        rating.setUser(user);
        rating.setBook(book);
        rating.setRating(star);
        ratingService.save(rating);
        book.setRating(rating.getRating());
        booksService.saveBook(book);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
