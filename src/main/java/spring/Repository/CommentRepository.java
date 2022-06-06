package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Entity.Model.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    @Query("delete from Comment u where u.user.userId=:userId and u.book.bookId=:commentId")
    void deleteUserAndComment(@Param("userId")String userId,@Param("commentId")String commentId);
    @Transactional
    @Modifying
    @Query("update Comment u set u.content=:content where u.commentId=:commentId")
    void updateComment(@Param("commentId")String commentId,@Param("content")String content);

    @Query("select u from Comment u where u.book.bookId=:bookId")
    List<Comment> findAllByBookId(@PathVariable("bookId")String bookId);
}
