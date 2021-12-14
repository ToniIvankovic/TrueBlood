package progi.megatron.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String jwtSecret = System.getenv("JWT_SECRET");
    private final String jwtIssuer = "trueblood";

    public String generateAccessToken(User user) {
        if(jwtSecret == null) {
            System.out.println("JWT_SECRET not set!");
        }

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId())) //was 'userid,username'
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature - {} " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token - {} " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token - {} " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token - {} " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty - {} " + ex.getMessage());
        }
        return false;
    }
}
