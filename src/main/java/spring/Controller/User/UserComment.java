package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Comment;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.BookService;
import spring.Service.CommentService;
import spring.Service.UserService;

import java.util.Map;

@RestController
public class UserComment {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @PostMapping("/user/luu-comment/{bookId}")
    public ResponseEntity<?> saveComment(@PathVariable(name = "bookId")String bookId,@RequestBody Comment comment){
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        comment.setUser(user);
        comment.setBook(bookService.findBookByBookId(bookId));
        commentService.saveComment(comment);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }
    @PostMapping("/user/xoa-comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId")String commentId){
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        commentService.deleteUserAndComment(user.getUserId(),commentId);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }
    @PostMapping("/user/sua-comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId")String commentId,@RequestBody Map<String,Object> content){
        commentService.updateComment(commentId, content.get("conten").toString());
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }
    @GetMapping("/book/comment/{bookId}")
    public ResponseEntity<?> commentBook(@PathVariable(name = "bookId")String bookId){
        return new ResponseEntity<>(commentService.findAllByBookId(bookId), HttpStatus.OK);
    }
}
