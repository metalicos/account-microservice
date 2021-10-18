package ua.com.cyberdone.account.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.cyberdone.account.dto.account.AccountDto;
import ua.com.cyberdone.account.dto.token.TokenDto;
import ua.com.cyberdone.account.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String BEARER = "Bearer ";

    @Value("${security.jwt-secret}")
    private String jwtSecret;

    @Value("${security.jwt-expiration-time-ms}")
    private Long jwtExpirationTimeInMs;

    private final ObjectMapper mapper;

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parseToken(token)).getBody();
    }

    public String generateToken(AccountDto accountDto) throws JsonProcessingException {
        Claims claims = Jwts.claims().setSubject(accountDto.getUsername());
        claims.put("roles", mapper.writeValueAsString(accountDto.getRoles().toArray(new Role[0])) );
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(accountDto.getUsername())
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Role[] getRoles(String token) {
        return extractAllClaims(token).get("roles", Role[].class);
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtExpirationTimeInMs);
    }

    public Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public String parseToken(String token) {
        return StringUtils.hasText(token) && token.startsWith(BEARER) ? token.substring(BEARER.length()) : token;
    }

    public TokenDto getEmptyToken() {
        return TokenDto.builder()
                .token("")
                .build();
    }

    public boolean isValidToken(String jwtToken) {
        if (nonNull(jwtToken) && !jwtToken.isBlank() && jwtToken.startsWith(BEARER)) {
            String token = parseToken(jwtToken);
            if (validateJwtToken(token)) {
                return !isTokenExpired(token);
            }
        }
        return false;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Long getJwtExpirationTimeInMs() {
        return jwtExpirationTimeInMs;
    }
}
