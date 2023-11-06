package chat.jamsi.domain.translation.dto;

import chat.jamsi.domain.translation.dto.validator.ValidLanguage;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class TranslationDto {
    @NotBlank
    @ValidLanguage
    private String langFrom;
  
    @NotBlank
    @ValidLanguage
    private String langTo;

    @NotEmpty
    private String[] messages;
}
