package spring.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Book;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query("SELECT u FROM Book u where u.nameBook like %:keyword% or u.category.nameCate like %:keyword% or u.publishYear like %:keyword% or u.author like %:keyword%")
    List<Book> searchBook(@Param("keyword") String keyword);

    @Query("select u from Book u where u.category.categoryId=:categoryId")
    List<Book> findBooksByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

    @Query("select u from Book u where u.bookId=:bookId")
    Book findBooksByBookId(@Param("bookId") String bookId);

    @Transactional
    @Modifying
    @Query("update Book u set u.count=:count where u.bookId=:bookId")
    void findBookAndUpdate(@Param("count") Integer count, @Param("bookId") String bookId);

    @Query("select u from Book u where u.count<>0")
    Page<Book> getAllBooks(Pageable pageable);

    @Query("select u from Book u")
    Page<Book> getAllBook(Pageable pageable);

    @Query("select u from Book u where u.rating>3")
    List<Book> getBookByRating(Pageable pageable);

    @Query("SELECT u.nameBook FROM Book u where u.nameBook like %:keyword% or u.category.nameCate like %:keyword% or u.publishYear like %:keyword% or u.author like %:keyword%")
    List<String> searchAuto(@Param("keyword") String keyword);

    @Query("select u from Book u where u.author like %:tacgia% and u.price>=:giathap and u.price<=:giacao and u.publishYear like %:namsb% and u.category.categoryId like %:loai%")
    List<Book> findBookByCondition(@Param("tacgia") String tacgia, @Param(("giathap")) Integer giathap,
                                   @Param("giacao") Integer giacao, @Param("namsb") String namsb,@Param("loai")String loai, Pageable pageable);

    @Query("select count (u) from Book u where u.author like %:tacgia% and u.price>=:giathap and u.price<=:giacao and u.publishYear like %:namsb% and u.category.categoryId like %:loai%")
    Integer findBookByCondition(@Param("tacgia") String tacgia, @Param(("giathap")) Integer giathap,
                                @Param("giacao") Integer giacao, @Param("namsb") String namsb,@Param("loai")String loai);

    @Query("select u from Book u where u.category.categoryId=:categoryId")
    List<Book> findBooksByCategoryId(@Param("categoryId") String categoryId);

    @Query("select distinct (u.author) from Book u where u.category.categoryId=:categoryId order by u.author asc")
    List<String> findAuthor(@Param("categoryId")String categoryId);

    @Query("select distinct (u.price) from Book u where u.category.categoryId=:categoryId order by u.price asc")
    List<Integer> findPrice(@Param("categoryId")String categoryId);

    @Query("select distinct (u.publishYear) from Book u where u.category.categoryId=:categoryId order by u.publishYear desc ")
    List<String> findYearPublic(@Param("categoryId")String categoryId);

    @Query("select u from Book u where u.count<>0")
    List<Book> countBook();

    @Query("select max (u.price) from Book u")
    Integer maxPrice();
}
