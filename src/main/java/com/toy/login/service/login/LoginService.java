package com.toy.login.service.login;

import com.toy.login.model.dto.MemberDto;

public interface LoginService {
    Object signup(MemberDto.SignupRequest signupRequest);
}