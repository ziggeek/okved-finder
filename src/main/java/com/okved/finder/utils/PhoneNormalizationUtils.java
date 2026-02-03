package com.okved.finder.utils;

import com.okved.finder.exception.PhoneNormalizationException;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Сервис для нормализации российских мобильных телефонных номеров.
 *
 * <p>Принимает номер в произвольном формате и приводит его к стандартному виду +79XXXXXXXXX.
 * Выполняет валидацию на корректность российского мобильного номера.</p>
 *
 * @author OKVED Finder Team
 * @version 1.0.0
 */
@UtilityClass
public class PhoneNormalizationUtils {


    /**
     * Паттерн для извлечения только цифр из строки.
     */
    private static final Pattern DIGITS_ONLY = Pattern.compile("\\D");

    /**
     *
     *         // Регулярное выражение:
     *         // ^ - начало строки
     *         // (?=(?:[^0-9]*[0-9]){11}[^0-9]*$) - позитивный просмотр вперед,
     *         // проверяет, что в строке ровно 11 цифр
     *         // [0-9+\\-()\\s]+ - разрешенные символы
     *         // $ - конец строки
     *
     */
    private static final String VALID_PHONE_NUMBER_REGEX ="^(?=(?:[^0-9]*[0-9]){11}[^0-9]*$)[0-9+\\-()\\s]+$";

    private static final String RUSSIAN_MOBILE_PHONE_CODE = "+7";



    /**
     * Проверяет, что строка содержит ровно 11 цифр
     * и только разрешенные символы: + ( ) - и пробелы
     *
     * @param phoneNumber строка для проверки
     * @return true если строка содержит ровно 11 цифр и только разрешенные символы
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        if (!Pattern.matches(VALID_PHONE_NUMBER_REGEX, phoneNumber)) {
            return false;
        }

        String digitsOnly = getDigitsOnlyFrom(phoneNumber);

        return digitsOnly.length() == 11 && (digitsOnly.charAt(0) == '7' || digitsOnly.charAt(0) == '8');
    }

    public String normalize(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new PhoneNormalizationException("Невалидный номер телефона");
        }

        return RUSSIAN_MOBILE_PHONE_CODE + getDigitsOnlyFrom(phoneNumber).substring(1);
    }

    private String getDigitsOnlyFrom(String phoneNumber) {
        return DIGITS_ONLY.matcher(phoneNumber).replaceAll("");
    }

}
