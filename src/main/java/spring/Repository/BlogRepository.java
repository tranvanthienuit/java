package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Blog;

import javax.transaction.Transactional;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> {
    void deleteByBlogId(String blogId);

    @Transactional
    @Modifying
    @Query("update Blog u set u.context=:content where u.blogId=:blodId")
    void findAndUpdateBlog(String blogId,String content);
}
