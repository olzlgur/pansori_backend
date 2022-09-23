package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.CommonResponse;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ResponseService {

    public <T> CommonResponse<Object> getSuccessResponse(String msg, T data){
        return CommonResponse.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .msg(msg)
                .data(data)
                .build();
    }

    public CommonResponse<Object> getFailureResponse(Integer code,String msg){
        return CommonResponse.builder()
                .success(false)
                .code(code)
                .msg(msg)
                .build();
    }

    public void convertHttpToResposne(HttpServletResponse response, CommonResponse commonResponse) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject responseJson = new JSONObject();

        responseJson.put("code", commonResponse.getCode());
        responseJson.put("success", commonResponse.getSuccess());
        responseJson.put("msg", commonResponse.getMsg());
        responseJson.put("data", commonResponse.getData());

        response.getWriter().print(responseJson);
    }
}
