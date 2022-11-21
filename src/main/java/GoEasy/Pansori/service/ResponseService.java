package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.CommonResponse;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseService {

    public <T> CommonResponse<Object> getSuccessResponse(String msg, T data){
        return CommonResponse.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message(msg)
                .data(data)
                .build();
    }
}
