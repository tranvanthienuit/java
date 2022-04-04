package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token,String> {
}
