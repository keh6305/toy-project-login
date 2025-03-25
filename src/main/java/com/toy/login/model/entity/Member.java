package com.toy.login.model.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member {
    private Long memberId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String createdAt;
    private String deletedAt;
}