package GoEasy.Pansori.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonResponse<T> {

    private Boolean success;
    private int code;
    private String msg;
    private T data;

    @Builder
    public CommonResponse(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
