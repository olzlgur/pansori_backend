package GoEasy.Pansori.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResult<T> extends CommonResult{
    private T data;
}
