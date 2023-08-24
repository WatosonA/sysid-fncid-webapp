package com.example.sysid.component.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * アプリケーション例外.
 */
@Getter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** コード */
    private final String code;

    /** HTTPステータスコード */
    private final HttpStatus statusCode;

    /**
     * コンストラクタ.
     *
     * @param code to set
     * @param statusCode to set
     * @param message to set
     */
    ApplicationException(String code, HttpStatus statusCode, String message) {
        super(message);
        this.code = code;
        this.statusCode = statusCode;
    }

    /**
     * コンストラクタ.
     *
     * @param code to set
     * @param statusCode to set
     */
    ApplicationException(String code, HttpStatus statusCode, Throwable cause) {
        super(cause);
        this.code = code;
        this.statusCode = statusCode;
    }

}
