package chat.jamsi.domain.auth.controller;

import chat.jamsi.domain.auth.dto.request.SignInRequest;
import chat.jamsi.domain.common.dto.TokenDto;
import chat.jamsi.domain.member.domain.Member;
import chat.jamsi.domain.auth.dto.request.SignUpRequest;
import chat.jamsi.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader(name = "Authorization") String accessToken) {
        authService.logout(accessToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestHeader(name = "Authorization") String refreshToken) {
        return ResponseEntity.ok(authService.reissue(refreshToken));
    }

}
