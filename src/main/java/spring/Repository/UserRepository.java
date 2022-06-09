package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.User;
import spring.Entity.month_user;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    @Transactional
//    @Modifying
//    @Query("delete from User u where u.userId=:idUser")
//    void removeUserByUserId(@Param("idUser") String idUser);

    User findByNameUser(String username);

//    @Transactional
//    @Modifying
//    @Query("update User u set u.nameUser=:nameUser where u.userId=:userId")
//    void editUserName(@Param("nameUser") String nameUser, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.password=:pass where u.userId=:userId")
//    void editUserPass(@Param("pass") String pass, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.address=:address where u.userId=:userId")
//    void editUserAdress(@Param("address") String address, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.email=:email where u.userId=:userId")
//    void editUserEmail(@Param("email") String email, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.telephone=:telephone where u.userId=:userId")
//    void editUserTelephone(@Param("telephone") String telephone, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.sex=:sex where u.userId=:userId")
//    void editUserSex(@Param("sex") String sex, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.image=:image where u.userId=:userId")
//    void editImage(@Param("image") byte[] image, @Param("userId") String userId);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.fullName=:fullName where u.userId=:userId")
//    void editUserFullname(@Param("fullName") String fullName, @Param("userId") String userId);

    User findUserByUserId(String userId);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:pass where u.email=:email")
    void setPassword(@Param("pass") String pass, @Param("email") String email);

    @Query("select new spring.Entity.month_user(month(u.dayAdd),count(u.userId)) from User u group by month(u.dayAdd)")
    List<month_user> getUserAndMonnth();

    User findByEmail(String mail);
    @Query("select u from User u where u.role.nameRole=:roleName")
    List<User> findUsersByRole(@Param("roleName")String roleName);
}
