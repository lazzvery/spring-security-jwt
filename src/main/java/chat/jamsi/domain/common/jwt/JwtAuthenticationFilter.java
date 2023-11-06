package chat.jamsi.domain.common.jwt;

import chat.jamsi.domain.common.exception.CustomException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        response.setCharacterEncoding("utf-8");

        try {
            if (token != null && jwtTokenProvider.isValidateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.error("토큰이 비어 있습니다.");
                request.setAttribute("exception", CustomException.TOKEN_EMPTY.getMessage());
            }
        } catch (SignatureException e) {
            log.error("잘못된 JWT 서명입니다.");
            request.setAttribute("exception", CustomException.WRONG_TYPE_SIGNATURE.getMessage());
        } catch (MalformedJwtException e) {
            log.error("해당 토큰은 유효한 토큰이 아닙니다.");
            request.setAttribute("exception", CustomException.NOT_VALID_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            request.setAttribute("exception", CustomException.TOKEN_EXPIRED.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
            request.setAttribute("exception", CustomException.WRONG_TYPE_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT Claims 문자열이 비어있습니다.");
            request.setAttribute("exception", CustomException.EMPTY_CLAIMS_TOKEN.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
