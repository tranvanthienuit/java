//package spring.Entity.Model;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Token")
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//@Data
//public class Token {
//    @Id
//    @Column(name = "tokenRefresh")
//    private String tokenRefesh;
//    @OneToOne
//    @JoinColumn(name = "user")
//    private User user;
//
//    public Token(String jwt) {
//        this.tokenRefesh = jwt;
//    }
//}
