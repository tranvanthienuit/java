package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Orderss;
import spring.Entity.Model.User;
import spring.Entity.month_book;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderssRepository extends JpaRepository<Orderss, String> {
//    @Transactional
//    @Modifying
//    @Query("delete from Orderss u where u.OrderssId=:orderssId")
//    void removeOrderssByOrderssId(@Param("orderssId") String orderssId);

    @Query("select u from  Orderss u where u.OrderssId=:orderssId")
    Orderss findOrderssByOrderssId(@Param("orderssId") String orderssId);

    @Query("select u from Orderss u where u.OrderssDate=:date and u.user=:user")
    Orderss findOrderssByOrderssDateAndUserId(Date date, User user);

    @Query("select u from Orderss u where u.user.fullName=:fullName")
    List<Orderss> findOrderssByUser(String fullName);

    List<Orderss> findOrderssByAddress(String address);
    List<Orderss> findOrderssByStatus(String status);

    @Query("select new spring.Entity.month_book(month(u.OrderssDate),sum(u.totalBook)) from Orderss u group by month(u.OrderssDate)")
    List<month_book> getBookAndMonth();
}
