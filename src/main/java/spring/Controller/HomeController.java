package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import spring.Entity.*;
import spring.Entity.Model.*;
import spring.JWT.JwtTokenProvider;
import spring.Repository.MailService;
import spring.Sercurity.userDetail;
import spring.Service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static spring.JWT.JwtAuthenticationFilter.getJwtFromRequest;


@RestController
public class HomeController {
    @Autowired
    BookService booksService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    RatingService ratingService;

    @GetMapping(value = {"/trang-chu/{page}", "/trang-chu"})
    public ResponseEntity<BookReturn> home(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BookReturn bookReturn = new BookReturn();


        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        //lấy tất cả các sách và số lưởng tổng
        Pageable pageable = PageRequest.of(page, 16);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(bookPageContent.size());

        // lấy sách dựa trên những phiếu mượn sách trước
        Pageable pageable1 = PageRequest.of(0, 16);
        List<Book> bookList1 = orderssDeSevice.getBookFromBorrDe(pageable1);

        // lấy sách dựa trên số sách mà khách hàng đã mượn
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserName(userName);
        List<Book> bookUser = orderssDeSevice.getBookFromBorrDeAndUser(pageable1, user);

        // lấy sách dựa trên số sao đánh giá cao nhất
        List<BookRating> bookRatings = ratingService.bookRating();

        if (bookUser.isEmpty()) {
            bookReturn.setBookList(bookList);
            bookReturn.setBooks(bookList1);
            bookReturn.setBookRatings(bookRatings);
            return new ResponseEntity<>(bookReturn, HttpStatus.OK);
        } else {
            bookReturn.setBookList(bookList);
            bookReturn.setBooks(bookUser);
            bookReturn.setBookRatings(bookRatings);
            return new ResponseEntity<>(bookReturn, HttpStatus.OK);
        }


    }


    @GetMapping(value = {"/thu-vien/{page}", "/thu-vien"})
    public ResponseEntity<BookReturn> shop(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BookReturn bookReturn = new BookReturn();


        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(bookPageContent.size());


        Pageable pageable1 = PageRequest.of(0, 16);
        List<Book> bookList1 = orderssDeSevice.getBookFromBorrDe(pageable1);


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserName(userName);
        List<Book> bookUser = orderssDeSevice.getBookFromBorrDeAndUser(pageable1, user);


        List<BookRating> bookRatings = ratingService.bookRating();

        if (bookUser == null) {
            bookReturn.setBookList(bookList);
            bookReturn.setBooks(bookList1);
            bookReturn.setBookRatings(bookRatings);
            return new ResponseEntity<>(bookReturn, HttpStatus.OK);
        } else {
            bookReturn.setBookList(bookList);
            bookReturn.setBooks(bookUser);
            bookReturn.setBookRatings(bookRatings);
            return new ResponseEntity<>(bookReturn, HttpStatus.OK);
        }

    }


    @GetMapping(value = {"/xem-tai-khoan"})
    public ResponseEntity<User> getUser() throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping(value = {"/loai-sach/{CategoryId}", "/loai-sach"})
    public ResponseEntity<List<Categories>> getCategoryBook(@RequestBody @PathVariable(value = "CategoryId", required = false) String CategoryId) throws Exception {
        if (CategoryId == null) {
            return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
        } else {
            List<Categories> categoriesList = categoryService.findByCategoryId(CategoryId);
            if (categoriesList == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(categoriesList, HttpStatus.OK);
        }
    }


    @PostMapping("/dang-nhap")
    public LoginResponse getlogin(@RequestBody LoginReQuest loginReQuest) throws Exception {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginReQuest.getNameUser(),
                loginReQuest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userDetail user = (userDetail) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getUsername());
        Token token = new Token();
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId(), user.getUsername());
        token.setTokenRefesh(refreshToken);
        User user1 = userService.findUserByUserId(user.getUserId());
        token.setUser(user1);
        tokenService.saveToken(token);
        return new LoginResponse(accessToken, refreshToken);
    }

    @GetMapping("/dang-xuat")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        if (auth != null) {
            List<Token> tokenList = tokenService.getAllToken();
            for (Token token : tokenList) {
                if (token.getUser().getUserId().equals(user.getUserId())) {
                    tokenService.removeToken(token);
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                    return new ResponseEntity<>("successful", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dang-ky")
    public ResponseEntity<User> getregister(@RequestBody User user) throws Exception {
        String username;
        if (userService.findUserName(user.getNameUser()) == null) {
            username = "";
        } else {
            username = userService.findUserName(user.getNameUser()).getNameUser();
        }
        if (user.getNameUser().equals(username)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.fineRoleByName("ADMIN");
        user.setRole(role);
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        user.setDayAdd(date);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        Token token = new Token(jwt);
        List<Token> tokenList = tokenService.getAllToken();
        for (Token token1 : tokenList) {
            System.out.println(token.getTokenRefesh().equals(token1.getTokenRefesh()));
            if (token.getTokenRefesh().equals(token1.getTokenRefesh())) {
                String userId = tokenProvider.getUserIdFromJWT(jwt);
                User user = userService.findUserByUserId(userId);
                String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getNameUser());
                return ResponseEntity.ok(new LoginResponse(accessToken, token.getTokenRefesh()));
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping(value = {"/danh-gia-sach/{bookId}/{star}", "/danh-gia-sach"})
    public void appriciateBook(@PathVariable(value = "bookId", required = false) String bookId, @PathVariable(value = "star", required = false) int star) {
        Rating rating = new Rating();
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        Book book = booksService.findBooksByBookId(bookId);
        rating.setUser(user);
        rating.setBook(book);
        rating.setRating(star);
        ratingService.save(rating);
    }

    @GetMapping("/category")
    public ResponseEntity<CateList> getAllCategory() {
        CateList cateList = new CateList();
        cateList.setCategoriesList(categoryService.getAllCategory());
        cateList.setCount(categoryService.getAllCategory().size());
        return new ResponseEntity<>(cateList, HttpStatus.OK);
    }
}
