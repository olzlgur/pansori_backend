package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.domain.response.SuccessResult;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public enum CommonResponse {
        SUCCESS(0, "성공하였습니다.");

        int code;
        String msg;

        CommonResponse(int code, String  msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    // 단일 건에 대한 결과 처리 메서드
    public <T> SuccessResult<T> getResult(T data){
        SuccessResult<T> result = new SuccessResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 다중 건에 대한 결과 처리 메서드
//    public <T> ListResult<T> getListResult(List<T> list) {
//        ListResult<T> result = new ListResult<>();
//        result.setList(list);
//        setSuccessResult(result);
//        return result;
//    }

    // 성공 결과 처리 메서드
    public SuccessResult getSuccessResult() {
        SuccessResult result = new SuccessResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리하는 메서드
    public SuccessResult getFailResult(int code, String msg){
        SuccessResult result = new SuccessResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }

    public void setSuccessResult(CommonResult result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
