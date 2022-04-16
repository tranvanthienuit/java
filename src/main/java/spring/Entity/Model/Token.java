package spring.Entity.Model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Token")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Data
public class Token {
    @Id
    @Column(name = "tokenRefresh")
    private String tokenRefesh;

    public Token(String jwt) {
        this.tokenRefesh = jwt;
    }
}
