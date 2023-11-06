package chat.jamsi.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 토큰 */
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "해당 토큰은 유효한 토큰이 아닙니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    EMPTY_CLAIMS_TOKEN(HttpStatus.UNAUTHORIZED, "JWT Claims 문자열이 비어있습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "올바르지 않은 JWT 토큰입니다."),

    /* 회원 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원 입니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "아이디나 비밀번호가 일치하지 않습니다."),

    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
