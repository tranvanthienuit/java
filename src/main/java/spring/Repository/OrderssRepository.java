package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Orderss;
import spring.Entity.Model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderssRepository extends JpaRepository<Orderss, String> {
    @Transactional
    @Modifying
    @Query("delete from Orderss u where u.OrderssId=:idOrderss")
    void removeOrderssByOrderssId(@Param("idOrderss") String idOrderss);

    @Query("select u from  Orderss u where u.OrderssId=:idOrderss")
    Orderss findOrderssByOrderssId(@Param("idOrderss") String idOrderss);

    @Query("select u from Orderss u where u.OrderssDate=:date and u.user=:user")
    Orderss findOrderssByOrderssDateAndUserId(Date date, User user);

    @Query("select u from Orderss u where u.user.nameUser=:userName")
    List<Orderss> findOrdersssByUser(String userName);

    @Query("select month(u.OrderssDate),sum(u.totalBook) from Orderss u group by month(u.OrderssDate)")
    Map<Integer, Integer> getBookAndMonth();
}
