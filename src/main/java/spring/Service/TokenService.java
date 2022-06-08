//package spring.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spring.Entity.Model.Token;
//import spring.Repository.TokenRepository;
//
//import java.util.List;
//
//@Service
//public class TokenService {
//    @Autowired
//    TokenRepository tokenRepository;
//
//    public List<Token> getAllToken() {
//        return tokenRepository.findAll();
//    }
//
//    public Token saveToken(Token token) {
//        return tokenRepository.save(token);
//    }
//
//    public void removeToken(Token token) {
//        tokenRepository.delete(token);
//    }
//}
