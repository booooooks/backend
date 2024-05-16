package com.example.bookbackend.token.exception;

import com.example.bookbackend.common.exception.GlobalException;
import com.example.bookbackend.common.response.ApiCode;

public class TokenException extends GlobalException {

    public TokenException(ApiCode apiCode) {
        super(apiCode);
    }

    public TokenException(String addMessage, ApiCode apiCode) {
        super(addMessage, apiCode);
    }
}
