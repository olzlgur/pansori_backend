package GoEasy.Pansori.service;

import GoEasy.Pansori.config.jwt.JwtProvider;
import GoEasy.Pansori.domain.RefreshToken;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.token.TokenDto;
import GoEasy.Pansori.dto.member.LoginRequestDto;
import GoEasy.Pansori.repository.MemberRepository;
import GoEasy.Pansori.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto login(LoginRequestDto request){
        Member member = Member.createMemberByEmailAndPW(request.getEmail(), request.getPassword());

        // Login id/pw 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

        // 실제 검증이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomeUserDetailService에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

         member = memberRepository.findByEmail(member.getEmail()).get(); //인증 성공 후 멤버 정보 가져오기

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtProvider.generateToken(member);

        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(member.getEmail())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissueToken(TokenDto tokenDto){

        // RefreshToken 적용
        if(!jwtProvider.validateToken(tokenDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        // AccessToken에서 Member ID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenDto.getAccessToken());

        // 저장소에서 Member ID 기반으로 Refresh Token 값 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // Refresh 토큰 일치하는지 검사
        if(!refreshToken.getValue().equals(tokenDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        Member member = memberRepository.findByEmail(authentication.getName()).get();

        // 새로운 토큰 생성
        TokenDto newToken = jwtProvider.generateToken(member);

        // 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(newToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return newToken;
    }

}
