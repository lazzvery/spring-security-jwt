package chat.jamsi.domain.common.exception;

import chat.jamsi.domain.common.dto.response.ErrorResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        log.warn("handleCustomException {}", e.getMessage());
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

}
