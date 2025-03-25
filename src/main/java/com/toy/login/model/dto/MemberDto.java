package com.toy.login.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

public class MemberDto {
    @Data
    @Alias("SignupRequest")
    public static class SignupRequest {
        private String username;
        private String password;
        private String name;
        private String email;
        private String phone;
        private String role;
    }
}