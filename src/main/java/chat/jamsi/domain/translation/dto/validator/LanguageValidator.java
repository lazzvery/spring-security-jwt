package chat.jamsi.domain.translation.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class LanguageValidator implements ConstraintValidator<ValidLanguage, String> {
    private static final Set<String> LANGUAGES = Set.of(
            "af", "sq", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "ny", "zh-TW", "hr",
            "cs", "da", "nl", "en", "eo", "et", "tl", "fi", "fr", "gl", "ka", "de", "el", "gu", "ht", "ha",
            "iw", "hi", "hmn", "hu", "is", "ig", "id", "ga", "it", "ja", "jw", "kn", "kk", "km", "ko", "lo",
            "la", "lv", "lt", "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "fa", "pl",
            "pt", "ro", "ru", "sr", "st", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tg", "ta", "te",
            "th", "tr", "uk", "ur", "uz", "vi", "cy", "yi", "yo", "zu"
    );

    @Override
    public boolean isValid(String language, ConstraintValidatorContext context) {
        return LANGUAGES.contains(language);
    }
}
