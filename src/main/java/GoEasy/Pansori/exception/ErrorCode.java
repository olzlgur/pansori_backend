package GoEasy.Pansori.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 에러입니다."),

    INVALID_JWT_SIGN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),

    EXPIRED_JWT(HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다."),

    UNSPORRTED_JWT(HttpStatus.NOT_ACCEPTABLE, "지원되지 않는 JWT 토큰입니다."),

    INVALID_JWT(HttpStatus.BAD_REQUEST, "올바른 JWT 토큰이 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
