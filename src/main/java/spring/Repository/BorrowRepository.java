package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Borrow;
import spring.Entity.Model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String> {
    @Transactional
    @Modifying
    @Query("delete from Borrow u where u.borrowId=:idBorrow")
    void removeBorrowByBorrowId(@Param("idBorrow") String idBorrow);

    @Query("select u from  Borrow u where u.borrowId=:idBorrow")
    Borrow findBorrowByBorrowId(@Param("idBorrow") String idBorrow);

    @Query("select u from Borrow u where u.borrowDate=:date and u.user=:user")
    Borrow findBorrowByBorrowDateAndUserId(Date date, User user);

    List<Borrow> findBorrowsByUser(String userId);
}
