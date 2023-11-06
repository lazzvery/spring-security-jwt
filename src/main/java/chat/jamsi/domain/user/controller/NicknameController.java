package chat.jamsi.domain.user.controller;

import chat.jamsi.domain.user.service.NicknameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/nickname")
public class NicknameController {
    private final NicknameService nicknameService;

    @GetMapping("")
    public String getNickname() {
        return this.nicknameService.getNickname();
    }
}
