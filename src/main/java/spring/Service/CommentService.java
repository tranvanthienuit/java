package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Comment;
import spring.Repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
    public void deleteUserAndComment(String UserId,String CommentId){
        commentRepository.deleteUserAndComment(UserId,CommentId);
    }
    public void updateComment(String commentId,String content){
        commentRepository.updateComment(commentId, content);
    }
    public List<Comment> findAllByBookId(String bookId){
        return commentRepository.findAllByBookId(bookId);
    }
}
