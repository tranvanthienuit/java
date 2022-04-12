package spring.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Book;


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

    @Query("select u from Book u where u.bookId=:idBook")
    Book findBooksByBookId(@Param("idBook") String idBook);

    @Transactional
    @Modifying
    @Query("delete from Book u where u.bookId=:idBook")
    void removeBookByBookId(@Param("idBook") String idBook);

    @Transactional
    @Modifying
    @Query("update Book u set u.count=:count where u.bookId=:bookId")
    void findBookAndUpdate(@Param("count") Integer count, @Param("bookId") String bookId);

    @Query("select u from Book u where u.count<>0")
    Page<Book> getAllBooks(Pageable pageable);
}
