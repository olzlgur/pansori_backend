package GoEasy.Pansori.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FilterException extends RuntimeException{
    ErrorCode errorCode;
}
