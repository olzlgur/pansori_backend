package GoEasy.Pansori.config;

import GoEasy.Pansori.jwt.JwtAccessDeniedHandler;
import GoEasy.Pansori.jwt.JwtAuthenticationEntryPoint;
import GoEasy.Pansori.jwt.JwtProvider;
import GoEasy.Pansori.jwt.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 설정 disable
        http.csrf().disable()

                // exception handling 할 때 만든 에러 헨들링 클래스 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console을 위한 설정 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 하지만 JWT 세션을 사용하지 않기때문에 stateless로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .antMatchers("/api/auth/**").authenticated()
                .antMatchers(HttpMethod.POST,"/api/members").permitAll()
                .antMatchers(HttpMethod.GET, "/api/members").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/members/{\\d+}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/members/{\\d+}").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/mail/find/password").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/members/**").authenticated()
                .anyRequest().permitAll()

                //JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스 적용
                .and()
                .apply(new JwtSecurityConfig(jwtProvider));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}