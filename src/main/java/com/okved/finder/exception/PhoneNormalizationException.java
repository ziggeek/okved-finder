package com.okved.finder.exception;

/**
 * Исключение, выбрасываемое при невозможности нормализовать номер телефона.
 * 
 * <p>Содержит описание причины, по которой номер не может быть нормализован.</p>
 * 
 * @author OKVED Finder Team
 * @version 1.0.0
 */
public class PhoneNormalizationException extends RuntimeException {
    
    /**
     * Создает исключение с указанным сообщением.
     * 
     * @param message описание ошибки нормализации
     */
    public PhoneNormalizationException(String message) {
        super(message);
    }
    
    /**
     * Создает исключение с указанным сообщением и причиной.
     * 
     * @param message описание ошибки нормализации
     * @param cause исходное исключение
     */
    public PhoneNormalizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
