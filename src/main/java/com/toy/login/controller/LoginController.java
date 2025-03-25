package com.toy.login.controller;

import com.toy.login.model.dto.MemberDto;
import com.toy.login.model.dto.Response;
import com.toy.login.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signupPost(@RequestBody MemberDto.SignupRequest signupRequest) {
        return ResponseEntity.ok(Response.success(loginService.signup(signupRequest)));
    }
}