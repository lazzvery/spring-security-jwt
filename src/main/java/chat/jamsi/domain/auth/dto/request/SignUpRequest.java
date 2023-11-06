package chat.jamsi.domain.auth.dto.request;

import chat.jamsi.domain.member.domain.Member;
import chat.jamsi.domain.member.domain.Roles;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;

    @NotBlank(message = "언어는 필수로 입력해야 합니다.")
    private String language;

    public Member toMemberEntity(String encryptedPassword) {
        return Member.builder()
                .email(email)
                .password(encryptedPassword)
                .nickname(nickname)
                .language(language)
                .role(Roles.ROLE_MEMBER)
                .build();
    }

}
