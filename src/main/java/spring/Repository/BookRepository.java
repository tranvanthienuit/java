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
    @Query("select u from Book u where u.nameBook = :name")
    List<Book> findBooksByNameBook(@Param("name") String name);

    @Query("select u from Book u where u.author=:name")
    List<Book> findBooksByAuthor(@Param("name") String name);

    @Query("select u from Book u where u.category.nameCate=:name")
    List<Book> findBooksByCategoryName(@Param("name") String name);

    @Query("select u from Book u where u.category.categoryId=:categoryId")
    List<Book> findBooksByCategoryId(@Param("categoryId") String categoryId);

    @Query("select u from Book u where u.bookId=:bookId")
    Book findBooksByBookId(@Param("bookId") String bookId);

    @Transactional
    @Modifying
    @Query("delete from Book u where u.bookId=:bookId")
    void removeBookByBookId(@Param("bookId") String bookId);

    @Transactional
    @Modifying
    @Query("update Book u set u.count=:count where u.bookId=:bookId")
    void findBookAndUpdate(@Param("count") Integer count, @Param("bookId") String bookId);

    @Query("select u from Book u where u.count<>0")
    Page<Book> getAllBooks(Pageable pageable);

    @Query("select u from Book u")
    Page<Book> getAllBook(Pageable pageable);

    @Query("SELECT u FROM Book u where u.nameBook like %:keyword% or u.category.nameCate like %:keyword%")
    List<String> searchByNameBook(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query("delete from Book u where u.category.categoryId=:categoryId")
    void removeBookByCategory(@Param("categoryId") String categoryId);

    @Query(value = "select u from Book u where u.author=:tacgia or u.author = false and u.price<:giathap or u.price = false and u.price<:giacao or u.price = false and u.publishYear=:namsb or u.publishYear = false",nativeQuery = true)
    List<Book> findBookByCondition(@Param("tacgia") String tacgia, @Param(("giathap")) Integer giathap,
                                   @Param("giacao") Integer giacao, @Param("namsb") Integer namsb);
}
