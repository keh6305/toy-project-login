package com.toy.login.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response {
    private HttpStatus status;
    private String message;
    private Object data;

    public static Response success() {
        return Response.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS_MESSAGE)
                .build();
    }

    public static Response success(Object data) {
        return Response.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public static Response fail() {
        return Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL_MESSAGE)
                .build();
    }

    public static Response error() {
        return Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ResponseMessage.ERROR_MESSAGE)
                .build();
    }
}