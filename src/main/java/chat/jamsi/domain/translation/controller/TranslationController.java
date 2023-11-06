package chat.jamsi.domain.translation.controller;
import java.io.IOException;
import java.util.List;

import chat.jamsi.domain.translation.dto.TranslationDto;
import chat.jamsi.domain.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/translation")
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping("")
    public List<String> translate(@Valid @RequestBody TranslationDto translationDto) throws IOException {
        return this.translationService.translate(translationDto);
    }
}
