package erp.javaguides.erpbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }
    /**
     * Generates a JWT token for a given user email and role.
     * This method is the single source of truth for token generation.
     *
     * @ param userEmail The email of the user to set as the 'subject' of the token.
     * @ param role The role of the user. This will be added as a custom claim.
     * @return A signed JWT token as a String.
     */
    public String generateToken(Map<String , Object> extraClaims,String email) {
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        logger.info("The generated token inside the generate token method is : "+token);
        return token;
    }

    public String generateTokenWithRole(String username , String role){
        Map<String , Object> claims = new HashMap<>();
        // Ensure the role starts with "ROLE_". If not, add it.
        String fullRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        claims.put("role" , fullRole);
        logger.info("Generating token for user: {} with role: {}", username, fullRole);
        return generateToken(claims , username);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token , Function<Claims , T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUserName(String token){
        return extractClaim(token , Claims::getSubject);
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public boolean isTokenValid(String token , String username) {
        return extractUserName(token).equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token , Claims::getExpiration).before(new Date());
    }

}
