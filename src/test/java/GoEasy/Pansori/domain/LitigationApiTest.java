package GoEasy.Pansori.domain;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.litigation.LitRequestDto;
import GoEasy.Pansori.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LitigationApiTest {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void before(){
        //멤버 추가
        Member member = Member.createMemberByEmailAndPW("testEmail@gmail.com", "dmsgk123123123!A");
        memberService.join(member);
    }


    @Test
    public void 멤버_소송추가() throws Exception {
        //given
        Member member = memberService.findOneByEmail("testEmail@gmail.com");
        LitRequestDto requestDto = LitRequestDto.builder()
                .court("광주지방법원")
                .num_opposite(3)
                .cost(1000l)
                .sendCost(500l)
                .title("테스트 소송")
                .type(LitigationType.CIVIL)
                .build();

        Litigation litigation = Litigation.createLitigation(requestDto);

        //when
        memberService.addLitigation(member, litigation);

        //then
        List<Litigation> litigations = member.getLitigations();
        assertThat(litigations.size()).isEqualTo(1);
        assertThat(litigations.get(0).getTitle()).isEqualTo("테스트 소송");
    }

    @Test
    public void 멤버_소송삭제() throws Exception {
        //given
        Member member = memberService.findOneByEmail("testEmail@gmail.com");
        LitRequestDto requestDto = LitRequestDto.builder()
                .court("광주지방법원")
                .num_opposite(3)
                .cost(1000l)
                .sendCost(500l)
                .title("테스트 소송")
                .type(LitigationType.CIVIL)
                .build();

        Litigation litigation = Litigation.createLitigation(requestDto);
        memberService.addLitigation(member, litigation); //소송추가

        //when
        Long id = litigation.getId();
        memberService.deleteLitigation(member, id);

        //then
        assertThat(member.getLitigations().size()).isEqualTo(0);
    }

    @Test
    public void 멤버_소송조회() throws Exception {
        //given
        Member member = memberService.findOneByEmail("testEmail@gmail.com");
        Litigation litigation1 = createTempLitigation("테스트 소송1");
        memberService.addLitigation(member, litigation1); //소송추가
        Litigation litigation2 = createTempLitigation("테스트 소송2");
        memberService.addLitigation(member, litigation2); //소송추가
        Litigation litigation3 = createTempLitigation("테스트 소송3");
        memberService.addLitigation(member, litigation3); //소송추가
        Litigation litigation4 = createTempLitigation("테스트 소송4");
        memberService.addLitigation(member, litigation4); //소송추가

        //when
        List<Litigation> litigations = member.getLitigations();

        //then
        assertThat(litigations.size()).isEqualTo(4);
    }

    private Litigation createTempLitigation(String title){
        LitRequestDto requestDto = LitRequestDto.builder()
                .court("테스트")
                .num_opposite(3)
                .cost(1000l)
                .sendCost(500l)
                .title(title)
                .type(LitigationType.CIVIL)
                .build();

        return Litigation.createLitigation(requestDto);
    }

}