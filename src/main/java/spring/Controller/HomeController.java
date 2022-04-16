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
    BorrowDeSevice borrowDeSevice;
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
    MailService mailService;
    @Autowired RatingService ratingService;

    @GetMapping(value = {"/trang-chu/{page}", "/trang-chu"})
    public ResponseEntity<BookReturn> home(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        BookReturn bookReturn = new BookReturn();


        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 8);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(bookPageContent.size());


        Pageable pageable1 = PageRequest.of(0, 4);
        List<Book> bookList1 = borrowDeSevice.getBookFromBorrDe(pageable1);


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserName(userName);
        List<Book> bookUser = borrowDeSevice.getBookFromBorrDeAndUser(pageable1, user.getUserId());


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
        Pageable pageable = PageRequest.of(page, 8);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(bookPageContent.size());


        Pageable pageable1 = PageRequest.of(0, 4);
        List<Book> bookList1 = borrowDeSevice.getBookFromBorrDe(pageable1);


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserName(userName);
        List<Book> bookUser = borrowDeSevice.getBookFromBorrDeAndUser(pageable1, user.getUserId());


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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId(), user.getUsername());
        tokenService.saveToken(new Token(refreshToken));
        return new LoginResponse(accessToken, refreshToken);
    }

    @GetMapping("/dang-xuat")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String jwt = getJwtFromRequest(request);
            Token token = new Token(jwt);
            List<Token> tokenList = tokenService.getAllToken();
            for (Token token1 : tokenList) {
                if (token.getTokenRefesh().equals(token1.getTokenRefesh())) {
                    tokenService.removeToken(token);
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
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
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.fineRoleByName("USER");
        user.setRole(role);
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
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @PostMapping("/quen-mat-khau/{email}")
    public ResponseEntity<?> forgetPass(@PathVariable("email") String email) {
        Mail mail = new Mail();
        mail.setMailFrom("tranvanthienuit@gmail.com");
        mail.setMailTo(email);
        mail.setMailSubject("Quên password");
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String code =  String.format("%06d", number);
        userService.setPassword(passwordEncoder.encode(code),email);
        mail.setMailContent("Code: "+code);
        mailService.sendEmail(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = {"/danh-gia-sach/{bookId}/{star}","/danh-gia-sach"})
    public void appriciateBook(@PathVariable(value = "bookId",required = false)String bookId,@PathVariable(value = "star",required = false)int star){
        Rating rating = new Rating();
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        Book book = booksService.findBooksByBookId(bookId);
        rating.setUser(user);
        rating.setBook(book);
        rating.setRating(star);
        ratingService.save(rating);
    }
}
