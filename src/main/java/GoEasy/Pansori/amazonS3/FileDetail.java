package GoEasy.Pansori.amazonS3;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileDetail {
    private String id;
    private String name;
    private String format;
    private String path;
    private long bytes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static FileDetail multipartOf(Long memberId, MultipartFile multipartFile){

        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        final String fileRoot = memberId + "/";
        final String fileName = fileRoot + MultipartUtil.getFileName(multipartFile.getOriginalFilename());

        return FileDetail.builder()
                .id(fileId)
                .name(fileName)
                .format(format)
                .path(MultipartUtil.createPath(fileName, fileId, format))
                .bytes(multipartFile.getSize())
                .build();
    }
}
