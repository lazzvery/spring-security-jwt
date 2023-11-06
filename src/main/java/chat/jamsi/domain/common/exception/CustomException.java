package chat.jamsi.domain.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    public static final CustomException TOKEN_EMPTY = new CustomException(ErrorCode.TOKEN_EMPTY);
    public static final CustomException WRONG_TYPE_SIGNATURE = new CustomException(ErrorCode.WRONG_TYPE_SIGNATURE);
    public static final CustomException NOT_VALID_TOKEN = new CustomException(ErrorCode.NOT_VALID_TOKEN);
    public static final CustomException TOKEN_EXPIRED = new CustomException(ErrorCode.TOKEN_EXPIRED);
    public static final CustomException WRONG_TYPE_TOKEN = new CustomException(ErrorCode.WRONG_TYPE_TOKEN);
    public static final CustomException EMPTY_CLAIMS_TOKEN = new CustomException(ErrorCode.EMPTY_CLAIMS_TOKEN);
    public static final CustomException UNAUTHORIZED = new CustomException(ErrorCode.UNAUTHORIZED);

    public static final CustomException USER_NOT_FOUND = new CustomException(ErrorCode.USER_NOT_FOUND);
    public static final CustomException ALREADY_REGISTERED_USER = new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
    public static final CustomException INVALID_USER = new CustomException(ErrorCode.INVALID_USER);
    public static final CustomException DUPLICATE_RESOURCE = new CustomException(ErrorCode.DUPLICATE_RESOURCE);

    ErrorCode errorCode;

    protected CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 의도적 에러이므로 stack trace 제거
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
