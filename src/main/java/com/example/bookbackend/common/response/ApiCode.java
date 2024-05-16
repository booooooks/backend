package com.example.bookbackend.common.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ApiCode {
    //정상
    API_0000("0000", "정상 처리되었습니다.", OK),

    // auth
    API_1000("1000", "토큰이 존재하지 않거나 허가되지 않은 접근입니다.", UNAUTHORIZED),
    API_1001("1001", "해당 페이지에 접근 권한이 없습니다.", FORBIDDEN),
    API_1002("1002", "토큰이 만료됐습니다.", UNAUTHORIZED),
    API_1003("1003", "토큰이 위조됐습니다.", UNAUTHORIZED),
    API_1004("1004", "해당 토큰은 사용 불가한 토큰입니다.", UNAUTHORIZED),
    API_1005("1005", "토큰의 형식이 잘못됐습니다.", UNAUTHORIZED),
    API_1006("1006", "비밀번호가 일치하지 않습니다.", BAD_REQUEST),

    // member
    API_2000("2000", "중복되는 이메일입니다.", BAD_REQUEST),
    API_2001("2001", "일치하는 회원이 없습니다.", BAD_REQUEST),

    // token
    API_3000("3000", "일치하는 리프레쉬 토큰이 없습니다.", BAD_REQUEST),

    // etc
    API_9999("9999", "기타 시스템 오류입니다.", INTERNAL_SERVER_ERROR);

    private String code;
    private String msg;
    private HttpStatus status;
}
