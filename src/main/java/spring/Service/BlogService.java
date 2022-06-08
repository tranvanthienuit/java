package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Blog;
import spring.Repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;
    public void saveBlog(Blog blog){
        blogRepository.save(blog);
    }
    public void findAndDeleteBlog(String blogId){
        blogRepository.deleteByBlogId(blogId);
    }
    public List<Blog> findAllBlog(){
        return blogRepository.findAll();
    }
    public Blog findBlog(String blogId){
        return blogRepository.findBlogByBlogId(blogId);
    }
}
