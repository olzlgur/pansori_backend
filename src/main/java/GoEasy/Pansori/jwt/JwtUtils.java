package GoEasy.Pansori.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";


    public String getEmailFromRequestHeader(HttpServletRequest request){
        String token = resolveToken(request);
        Claims claims = parseClaims(token);
        return claims.get("email").toString();
    }

    public Boolean checkJWTwithID(HttpServletRequest request, Long _id){
        Long id = getIdFromRequestHeader(request);
        return id.equals(_id);
    }

    private Long getIdFromRequestHeader(HttpServletRequest request){
        String token = resolveToken(request);
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.get("id").toString());
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(accessToken).getBody();
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
