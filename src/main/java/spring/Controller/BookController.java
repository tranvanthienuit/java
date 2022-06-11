package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Book>> findBook(@RequestBody Map<String,Object> infoBook) throws Exception {
        if (infoBook != null) {
            if (!booksService.findBooksByNameBook(infoBook.get("infoBook").toString()).isEmpty()) {
                List<Book> booksList = booksService.findBooksByNameBook(infoBook.get("infoBook").toString());
                return new ResponseEntity<>(booksList, HttpStatus.OK);
            } else {
                if (!booksService.findBooksByAuthor(infoBook.get("infoBook").toString()).isEmpty()) {
                    List<Book> booksList = booksService.findBooksByAuthor(infoBook.get("infoBook").toString());
                    return new ResponseEntity<>(booksList, HttpStatus.OK);

                } else {
                    if (!booksService.findBooksByCategoryName(infoBook.get("infoBook").toString()).isEmpty()) {
                        List<Book> booksList = booksService.findBooksByCategoryName(infoBook.get("infoBook").toString());
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
        Pageable pageable = PageRequest.of(0, 6, Sort.by("dayAdd").descending());
        Page<Book> bookPage = booksService.getBookByDayAdd(pageable);
        List<Book> bookList = bookPage.getContent();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    @GetMapping(value = {"/search/book/{keyword}","/search/book"})
    public ResponseEntity<List<String>> searchByNameBook(@RequestBody @PathVariable(name = "keyword", required = false)String keywword){
        List<String> searchString = booksService.searchByNameBook(keywword);
        return new ResponseEntity<>(searchString,HttpStatus.OK);
    }
    @GetMapping(value = {"/search/{categoryId}","/search"})
    public ResponseEntity<?> findBookByCategory(@PathVariable(value = "categoryId", required = false)String categoryId){
        List<Book> bookList = booksService.findBooksByCategoryId(categoryId);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<?> findBookByCondition(@RequestParam(value = "tacgia",required = false)String tacgia,
                                                 @RequestParam(value = "giathap",required = false)Integer giathap,
                                                 @RequestParam(value = "giacao",required = false)Integer giacao,
                                                 @RequestParam(value = "namsb",required = false)Integer namsb,
                                                 @RequestParam(name = "page", required = false) Integer page){
        if (page == null)
            page = 0;
        Pageable pageable = PageRequest.of(page, 1);
        List<Book> bookList = booksService.findBookByCondition(tacgia, giathap, giacao, namsb,pageable);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
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
