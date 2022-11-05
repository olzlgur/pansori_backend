package GoEasy.Pansori.amazonS3;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class MultipartUtil {
    private static final String BASE_DIR = "images";

    //로컬에서 사용자 홈 디렉토리 경로 반환
    public static String getLocalHomteDirectory(){
        return System.getProperty("user.home");
    }

    //새로운 파일 고유 ID 생성
    public static String createFileId(){
        return UUID.randomUUID().toString();
    }

    //Multipart ContentType 값에서 확장자 반환
    public static String getFormat(String contentType){
        if(StringUtils.hasText(contentType)){
            return contentType.substring((contentType.lastIndexOf('/') +1));
        }
        return null;
    }

    //파일 전체 경로 생성
    public static String createPath(String fileId, String format){
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}
