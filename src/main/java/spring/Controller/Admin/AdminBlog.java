package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.Blog;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.BlogService;
import spring.Service.UserService;

import java.util.List;

@RestController
public class AdminBlog {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;



    @PostMapping("/admin/them-blog")
    public ResponseEntity<?> saveBlog(Blog blog) throws Exception{
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        blog.setUser(user);
        blogService.saveBlog(blog);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @GetMapping("/admin/xoa-blog")
    public ResponseEntity<?> deleteBlog(String blogId){
        blogService.findAndDeleteBlog(blogId);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @GetMapping("/admin/xem-tat-ca-blog")
    public ResponseEntity<List<Blog>> findAllBlog(){
        return new ResponseEntity<>(blogService.findAllBlog(),HttpStatus.OK);
    }

    @GetMapping("/admin/sua-blog")
    public ResponseEntity<?> updateBlog(Blog blog){
        blogService.findAndUpdateBlog(blog.getBlogId(), blog.getContext());
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
