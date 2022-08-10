package GoEasy.Pansori.api.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "litigation_id")
    private Litigation litigation;

    private String filename; // 파일 이름
    private String fileOriName; // 파일의 원래 이름
    private String fileUrl; // 파일 경로
    private String fileType; // 파일 유형
}
