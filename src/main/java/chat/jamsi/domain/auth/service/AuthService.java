package chat.jamsi.domain.auth.service;

import chat.jamsi.domain.auth.dto.request.SignInRequest;
import chat.jamsi.domain.common.dto.TokenDto;
import chat.jamsi.domain.common.exception.CustomException;
import chat.jamsi.domain.common.redis.RedisUtils;
import chat.jamsi.domain.common.security.CustomUserDetailsService;
import chat.jamsi.domain.common.jwt.JwtTokenProvider;
import chat.jamsi.domain.member.domain.Member;
import chat.jamsi.domain.auth.dto.request.SignUpRequest;
import chat.jamsi.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisUtils redisUtils;

    @Transactional
    public Member signUp(SignUpRequest request) {
        authRepository.findByEmail(request.getEmail())
                .ifPresent(member -> {
                    throw CustomException.ALREADY_REGISTERED_USER;
                });

        Member member = request.toMemberEntity(bCryptPasswordEncoder.encode(request.getPassword()));
        return authRepository.save(member);
    }

    @Transactional
    public TokenDto signIn(SignInRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        if (!bCryptPasswordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw CustomException.INVALID_USER;
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

        return jwtTokenProvider.createJwtTokenDto(authentication);
    }

    @Transactional
    public void logout(String accessToken) {
        accessToken = accessToken.substring(7);

        if (!jwtTokenProvider.isValidateToken(accessToken)) {
            throw CustomException.UNAUTHORIZED;
        }

        // 추후 @AuthenticationPrincipal 로 변경 필요, 예외처리 추가 필요
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        redisUtils.delete("RT:" + authentication.getName());

        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisUtils.setBlackList(accessToken, "access_token", expiration);
    }

    @Transactional
    public TokenDto reissue(String refreshToken) {
        refreshToken = refreshToken.substring(7);

        if (!jwtTokenProvider.isValidateToken(refreshToken)) {
            throw CustomException.UNAUTHORIZED;
        }

        // 추후 @AuthenticationPrincipal 로 변경 필요, 예외처리 추가 필요
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        redisUtils.validationRefreshToken(authentication.getName(), refreshToken);

        return jwtTokenProvider.createJwtTokenDto(authentication);
    }

}
