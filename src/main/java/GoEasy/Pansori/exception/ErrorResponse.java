package GoEasy.Pansori.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;

    private int status;

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }
    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

}
