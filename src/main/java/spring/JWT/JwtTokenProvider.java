package spring.JWT;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.Sercurity.userDetail;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "aaaaaa";
    private final long JWT_EXPIRATION = 604800000L;
    private final long JWT_EXPIRATION_REFRESH = 604800000L;


    public String generateAccessToken(String userId,String userName) {
        // Lấy thông tin user
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ username của user.
        return Jwts.builder()
                .setId(userId)
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public String generateRefreshToken(String userId,String userName) {
        // Lấy thông tin user
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_REFRESH);
        // Tạo chuỗi json web token từ username của user.
        return Jwts.builder()
                .setId(userId.toString())
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getId();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}


