package com.example.bookbackend.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

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

    public static <T> CommonResponse<T> from(ApiCode code, T info) {
        return new CommonResponse<>(code.name(), code.getMsg(), info);
    }
}
