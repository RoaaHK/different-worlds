package com.example.DifferentWorlds.JWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Function;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.util.Map;
import java.util.HashMap;
import java.security.Key;
import java.util.Date;


/// helper class that contains all utility methods, handling JWT
@Setter
@Getter
@Component
// TODO try to avoid util name
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${security.jwt.secret-key}")
    private String jwtSecret;
    @Value("${security.jwt.expiration-time}")

    private long jwtExpirationMS;
    private final Cache blacklistCache;

    public JwtUtils(CacheManager cacheManager) {
        this.blacklistCache = cacheManager.getCache("blacklist");
    }

    @Cacheable(value = "blacklist", key = "#token")
    public Boolean isTokenBlacklisted(String token) {
        return false;
    }

    @CacheEvict(value = "blacklist", key = "#token")
    public void blacklistToken(String token) {
        blacklistCache.put(token, Boolean.TRUE);
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            bearerToken.replace("Bearer ", "");
            return bearerToken.substring(7); /// remove Bearer
        }
        return null; /// returns null if the header is not present or doesn't start with "Bearer "
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, jwtExpirationMS);
    }
//
//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return generateToken(extraClaims, userDetails, jwtExpirationMS);
//    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return buildToken(extraClaims, userDetails, expiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) /// when token will be expired

                .signWith(getSignInKey(), SignatureAlgorithm.HS256) /// signs the token with a cryptographic key
                .compact(); /// converts the JWT to a string format
    }

    public String extractUsername(String token) {
                return extractAllClaims(token).getSubject();
        }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            logger.error("JWT processing failed", e);
            throw e;  // Propagate exception if needed
        }


    }
    /// sign and verify JWT, ensures the token's integrity and authenticity.
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public long getExpirationTime() {
        return jwtExpirationMS;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            /// validate the token
            Jwts.parserBuilder()  ///  extracting information from a JWT
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(authToken);
            /// if no exception is thrown, the token is valid
            return true;
        }catch (MalformedJwtException e) {
            logger.error("Malformed JWT token", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty", e);
        } catch (JwtException e) {
            logger.error("JWT processing failed", e);
        } catch (Exception e) {
            logger.error("Unexpected error during JWT validation", e);
        }
        return false;
    }
}
