package com.toy.login.repository;

import com.toy.login.model.dto.MemberDto;
import com.toy.login.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginRepository {
    Member loadUserByUsername(String username);

    int signup(MemberDto.SignupRequest signupRequest);
}