package spring.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.BookSelect;
import spring.Entity.Model.OrderssDetail;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Repository
public interface OrderssDeRepository extends JpaRepository<OrderssDetail, String> {
    @Transactional
    @Modifying
    @Query("delete from OrderssDetail u where u.OrderssDeId=:idOrderssDe")
    void removeByOrderssDeId(@Param("idOrderssDe") String idOrderssDe);

    @Transactional
    @Modifying
    @Query("delete from OrderssDetail u where u.OrderssDeId=:idOrderss ")
    void removeByOrderssId(@Param("idOrderss") String idOrderss);

    @Query("select u from OrderssDetail u where u.OrderssDeId=:idOrderssDe")
    OrderssDetail findOrderssDetailByOrderssDeId(String idOrderssDe);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from OrderssDetail u group by u.book.bookId order by sum(u.count) desc ")
    List<BookSelect> getBookFromBorrDe(Pageable pageable);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from OrderssDetail u where u.orderss.user.userId=:userId group by u.book.bookId order by sum (u.count) desc ")
    List<BookSelect> getBookFromBorrDeAndUser(Pageable pageable, @Param("userId") String userId);

    List<OrderssDetail> findOrderssDetailsByOrderss(String OrderssId);

    @Query("select sum(u.count),u.book.category.nameCate from OrderssDetail u group by u.book.category.nameCate")
    Map<Integer, String> getBookAndCategory();

    @Query("select month(u.orderss.OrderssDate),sum(u.total)from OrderssDetail u group by u.orderss.OrderssDate")
    Map<Integer, Double> getPriceAndMonth();
}
