package GoEasy.Pansori.amazonS3;

import GoEasy.Pansori.dto.File.FileSimpleDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "cloud.aws.s3", name = "bucket")
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String awsLink = "https://pansori.s3.ap-northeast-2.amazonaws.com/";
    private final String folderPrefix = "members/";

    public FileDetail save(Long memberId, MultipartFile multipartFile){
        FileDetail fileDetail = FileDetail.multipartOf(memberId, multipartFile);
        String fullPath = fileDetail.getPath();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileDetail;
    }

    public List<FileSimpleDto> findAll(Long memberId){
        String prefix = folderPrefix + memberId.toString() + "/";
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, prefix);
        List<FileSimpleDto> dtoList = new ArrayList<>();

        for (S3ObjectSummary item : objectListing.getObjectSummaries()) {
            String key = item.getKey();
            FileSimpleDto dto = FileSimpleDto.createDto(getFileNameInKey(prefix, key), awsLink + key);
            dtoList.add(dto);
        }

        return dtoList;
    }

    private String getFileNameInKey(String prefix, String key){
        return key.substring(prefix.length(), key.lastIndexOf("_"));
    }

    private String getKeyInLink(String link){
        return link.substring(awsLink.length());
    }

    public void remove(String link) {
        try{
            amazonS3Client.deleteObject(bucket, getKeyInLink(link));
        }
        catch (Exception e){
            throw new ApiException(ErrorCode.UNKNOWN_ERROR.getHttpStatus(), e.getMessage());
        }

    }
}
