package com.okved.finder.controller.response;

import com.okved.finder.entity.Okved;
import com.okved.finder.utils.PhoneNormalizationUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

import static com.okved.finder.utils.OkvedUtils.normalizeOkvedCode;

@Builder
@Schema(description = "Ответ запроса получения кода ОКВЭД по номеру телефона")
public record OkvedSearchResponse(

        @Schema(description = "Нормализованный номер мобильного телефонв", implementation = String.class)
        String normalizedPhone,

        @Schema(description = "Список ОКВЭД")
        List<OkvedResponse> okveds,

        @Schema(description = "Количество совпадений", implementation = Integer.class)
        int matchLength
) {

    public static OkvedSearchResponse from(@NonNull List<Okved> okveds, String phoneNumber) {
        int lenght = okveds.size() == 1 ? okveds.stream().findAny().get().getCode().length() : 0;
        return OkvedSearchResponse.builder()
                .normalizedPhone(PhoneNormalizationUtils.normalize(phoneNumber))
                .okveds(OkvedResponse.from(okveds))
                .matchLength(lenght)
                .build();
    }

    @Builder
    public record OkvedResponse(
            String okvedCode,
            String okvedName
    ) {

        public static OkvedResponse from(@NonNull Okved okved) {
            return OkvedResponse.builder()
                    .okvedCode(normalizeOkvedCode(okved.getCode()))
                    .okvedName(okved.getName())
                    .build();
        }

        public static List<OkvedResponse> from(@NonNull List<Okved> okveds) {
            return okveds.stream().map(OkvedResponse::from).toList();
        }
    }
}
