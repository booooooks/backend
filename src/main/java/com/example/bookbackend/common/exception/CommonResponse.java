package com.example.bookbackend.common.exception;

import com.example.bookbackend.common.util.ApiCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private final String code;
    private final String message;
    private T info;

    public CommonResponse(String code, String message, T info) {
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> CommonResponse<T> of(ApiCode code) {
        return new CommonResponse<>(code.name(), code.getMsg());
    }
}
