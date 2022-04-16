package spring.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.BookSelect;
import spring.Entity.Model.BorrowDetail;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface BorrowDeRepository extends JpaRepository<BorrowDetail, String> {
    @Transactional
    @Modifying
    @Query("delete from BorrowDetail u where u.borrowDeId=:idBorrowDe")
    void removeByBorrowDeId(@Param("idBorrowDe") String idBorrowDe);

    @Transactional
    @Modifying
    @Query("delete from BorrowDetail u where u.borrow=:idBorrow ")
    void removeByBorrowId(@Param("idBorrow") String idBorrow);

    BorrowDetail findBorrowDetailByBorrowDeId(String idBorrowDe);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from BorrowDetail u group by u.book.bookId order by sum(u.count) desc ")
    List<BookSelect> getBookFromBorrDe(Pageable pageable);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from BorrowDetail u where u.borrow.user.userId=:userId group by u.book.bookId order by sum (u.count) desc ")
    List<BookSelect> getBookFromBorrDeAndUser(Pageable pageable, @Param("userId") String userId);

    List<BorrowDetail> findBorrowDetailsByBorrow(String borrowId);
}
