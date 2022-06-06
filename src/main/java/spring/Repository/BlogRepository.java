package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Blog;

import javax.transaction.Transactional;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> {
    void deleteByBlogId(String blogId);

    @Transactional
    @Modifying
    @Query("update Blog u set u.content=:content where u.blogId=:blogId")
    void findAndUpdateBlog(@Param("blogId") String blogId,@Param("content") String content);
}
