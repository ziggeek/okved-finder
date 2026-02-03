package com.okved.finder.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

@UtilityClass
public class OkvedUtils {

    public static String normalizeOkvedCode(@NonNull String okvedCode) {
        switch (okvedCode.length()) {
            case 2:
                return okvedCode; // "12"

            case 3:
                return okvedCode.substring(0, 2) + "." +
                        okvedCode.substring(2); // "12.3"

            case 4:
                return okvedCode.substring(0, 2) + "." +
                        okvedCode.substring(2); // "12.34"

            case 5:
                return okvedCode.substring(0, 2) + "." +
                        okvedCode.substring(2, 4) + "." +
                        okvedCode.substring(4); // "12.34.5"

            case 6:
                return okvedCode.substring(0, 2) + "." +
                        okvedCode.substring(2, 4) + "." +
                        okvedCode.substring(4); // "12.34.56"

            default:
                // Не должно сюда попасть из-за проверок выше
                throw new IllegalArgumentException("Некорректная длина кода: " + okvedCode);
        }
    }
}
