package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Categories;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, String> {
    @Transactional
    @Modifying
    @Query("delete from Categories u where u.categoryId=:categoryId")
    void removeCategoriesByCategoryId(@Param("categoryId") String name);

    @Query("select u from Categories u where u.categoryId=:categoryid")
    List<Categories> findByCategoryId(@Param("categoryid") String categoryid);
}
