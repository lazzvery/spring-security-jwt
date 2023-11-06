package chat.jamsi.domain.common.jwt;

import chat.jamsi.domain.common.exception.CustomException;
import chat.jamsi.domain.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        if (exception.equals(CustomException.TOKEN_EMPTY.getMessage())) {
            setResponse(response, ErrorCode.TOKEN_EMPTY);
        }
        // 유효한 토큰이 아닌 경우
        else if(exception.equals(CustomException.NOT_VALID_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.NOT_VALID_TOKEN);
        }
        // 토큰 만료된 경우
        else if(exception.equals(CustomException.TOKEN_EXPIRED.getMessage())) {
            setResponse(response, ErrorCode.TOKEN_EXPIRED);
        }
        // 지원되지 않는 토큰인 경우
        else if(exception.equals(CustomException.WRONG_TYPE_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
        }
        // 잘못된 JWT 서명인 경우
        else if(exception.equals(CustomException.WRONG_TYPE_SIGNATURE.getMessage())) {
            setResponse(response, ErrorCode.WRONG_TYPE_SIGNATURE);
        }
        // JWT Claims 문자열이 비어있는 경우
        else if(exception.equals(CustomException.EMPTY_CLAIMS_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.EMPTY_CLAIMS_TOKEN);
        }

    }

    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("status", errorCode.getHttpStatus());

        response.getWriter().print(responseJson);
    }

}
