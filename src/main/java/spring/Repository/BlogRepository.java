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
//    @Transactional
//    @Modifying
//    @Query("delete from Blog u where u.blogId=:blogId")
//    void deleteByBlogId(@Param("blogId") String blogId);

    @Query("select u from Blog u where u.blogId=:blogId")
    Blog findBlogByBlogId(String blogId);
}
