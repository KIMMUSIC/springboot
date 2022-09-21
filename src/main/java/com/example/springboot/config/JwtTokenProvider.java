package com.example.springboot.config;

import com.example.springboot.jpa.RefreshToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.springboot.Service.CustomUserDetailService;
import com.example.springboot.jpa.RefreshTokenRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "myprojectsecret";

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    private final CustomUserDetailService customUserDetailService;
    private final RefreshTokenRepository refreshTokenRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + 10 * 60 * 1000L)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request, String header) {
        String Token = request.getHeader(header);
        if (Token != null ) {
            return Token;
        }
        return null;
    }


    public String resolveRefreshToken(HttpServletRequest request, String header) {
        Cookie[] Token = request.getCookies();

        for(Cookie cookie : Token){
            if(cookie.getName().equals(header)){
                return cookie.getValue();
            }
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public JwtCode validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return JwtCode.ACCESS;
        }catch(ExpiredJwtException e){
            return JwtCode.EXPIRED;
        }catch(Exception e){
            return JwtCode.DENIED;
        }
    }

    public String reissueRefreshToken(String refreshToken) throws RuntimeException {
        Authentication authentication = getAuthentication(refreshToken);
        RefreshToken findRefreshToken = refreshTokenRepository.findByUserId(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("userId : " + authentication.getName() + "was not found"));

        if (findRefreshToken.getToken().equals(refreshToken)) {
            String newRefreshToken = createRefreshToken();
            findRefreshToken.changeToken(newRefreshToken);
            return newRefreshToken;
        } else {
            return null;
        }

    }


    public static enum JwtCode{
        DENIED,ACCESS,EXPIRED;
        }
    }

