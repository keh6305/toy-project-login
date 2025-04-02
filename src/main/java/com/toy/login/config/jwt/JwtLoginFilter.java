package com.toy.login.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.login.config.security.CustomUserDetails;
import com.toy.login.model.dto.Response;
import com.toy.login.model.dto.ResponseMessage;
import com.toy.login.model.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // URL로 아이디와 비밀번호 전달
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);
//        return authenticationManager.authenticate(token);

        // Body로 아이디와 비밀번호 전달
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Member member = objectMapper.readValue(request.getReader(), Member.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword(), null);

            return authenticationManager.authenticate(token);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 권한 조회
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();

        String username = userDetails.getUsername();
        String role = grantedAuthority.getAuthority();

        String token = jwtUtil.createJwt(username, role, (60 * 60 * 10L));

        Response res = Response.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS_MESSAGE)
                .data(token)
                .build();

        // ObjectMapper를 사용하여 Response 객체를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(res);

        // 응답 헤더 설정
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 응답 본문에 JSON 전송
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized access");
    }
}