package com.example.springboot.Controller;

import com.example.springboot.DTO.LoginResponseDto;
import com.example.springboot.config.JwtTokenProvider;
import com.example.springboot.jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final DusersRepository dusersRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    // 로그인
    @PostMapping("/api/login")
    public LoginResponseDto login(HttpServletResponse response, @RequestBody Map<String, String> user) {
        log.info("user id = {} user password = {}", user.get("id"), user.get("password"));
        Dusers member = dusersRepository.findByUserId(user.get("id"));


        if(!passwordEncoder.matches(user.get("password"), member.getUserPassword())){
            throw new IllegalArgumentException("비밀번호 오류");
        }


        String access = jwtTokenProvider.createToken(member.getUserId(), member.getRoles());

        String refresh = jwtTokenProvider.createRefreshToken();
        RefreshToken refreshToken = RefreshToken.createToken(member.getUserId(), refresh);

        refreshTokenRepository.save(refreshToken);

        Cookie cookie = new Cookie("Refresh", refresh);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*7*24);

        response.addCookie(cookie);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserName(member.getUsername());
        loginResponseDto.setAccessToken(access);



        return loginResponseDto;

    }


    @GetMapping("/reissue")
    public String reissue(HttpServletResponse response, HttpServletRequest request){

        String token = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request, "Refresh");
        String Atoken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "AUTHORIZATION");
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token) == JwtTokenProvider.JwtCode.ACCESS) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            String userpk = jwtTokenProvider.getUserPk(Atoken);
            RefreshToken refreshToken = refreshTokenRepository.findByUserId(userpk).orElseThrow(()->new RuntimeException("토근에 유저정보가 없음"));

            Dusers member = dusersRepository.findByUserId(userpk);

            String access = jwtTokenProvider.createToken(member.getUserId(), member.getRoles());

            String newrefreshToken = jwtTokenProvider.createRefreshToken();
            refreshToken.changeToken(newrefreshToken);

            Cookie cookie = new Cookie("Refresh", newrefreshToken);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*7*24);

            response.addCookie(cookie);

            return access;

        }
        return "access token expired";
    }

    @GetMapping("/silent-refresh")
    public String silent_refresh(HttpServletResponse response, HttpServletRequest request){
        String token = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request, "Refresh");
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token) == JwtTokenProvider.JwtCode.ACCESS) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.

            RefreshToken refreshToken = refreshTokenRepository.findBytoken(token).orElseThrow(()->new RuntimeException("토근에 유저정보가 없음"));
            String user = refreshToken.getUserId();
            Dusers member = dusersRepository.findByUserId(user);


            String access = jwtTokenProvider.createToken(member.getUserId(), member.getRoles());

            String newrefreshToken = jwtTokenProvider.createRefreshToken();
            refreshToken.changeToken(newrefreshToken);

            Cookie cookie = new Cookie("Refresh", newrefreshToken);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*7*24);

            response.addCookie(cookie);

            return access;

        }
        return "refresh token expired";
    }
}
