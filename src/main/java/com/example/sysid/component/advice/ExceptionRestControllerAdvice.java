package com.example.sysid.component.advice;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.sysid.component.exception.ApplicationException;

/**
 * 例外ハンドラ。
 */
@RestControllerAdvice
public class ExceptionRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Value("${application.exception-handler.response.debug:false}")
    private boolean debuggable;

    /**
     * ResponseBody の BeanValidation のチェック結果をハンドルします.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationException(ex, ex.getBindingResult(), request);
    }

    /**
     * BeanValidation のチェック結果をハンドルします.
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        return handleValidationException(ex, ex.getBindingResult(), request);
    }

    /**
     * HTTPメッセージ変換エラーをハンドルします.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.getMessage());
        if (ex.getCause() instanceof Exception) {
            return handleExceptionInternal((Exception) ex.getCause(), null, headers, status, request);
        } else {
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    /**
     * アプリケーションエラーをハンドルします.
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        return handleBusinessException(ex, ex.getCode(), ex.getStatusCode(), request);
    }

    /**
     * 排他エラーをハンドルします.
     */
    @ExceptionHandler(ConcurrencyFailureException.class)
    public ResponseEntity<Object> handleConcurrencyFailureException(ConcurrencyFailureException ex,
            WebRequest request) {
        return handleBusinessException(ex, "CONCURRENCY_FAILURE", HttpStatus.CONFLICT, request);
    }

    /**
     * IO エラーをハンドルします.
     */
    @ExceptionHandler(value = { UncheckedIOException.class, IOException.class })
    public final ResponseEntity<Object> handleIoException(Exception ex, WebRequest request) {
        return handleSystemException(ex, "IO_ERROR", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    /**
     * システムエラーをハンドルします.
     */
    @ExceptionHandler(value = { SecurityException.class, Exception.class })
    public final ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        return handleSystemException(ex, "SYSTEM_ERROR", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    /**
     * クライアントにより切断された場合.
     * クライアントには返却されずにサーバにのみ出力.
     */
    @ExceptionHandler(value = ClientAbortException.class)
    public final void handleClientAbortException(ClientAbortException ex, WebRequest request) {
        logger.warn(ex.getClass().getName() + ":" + ex.getMessage());
    }

    /**
     * アプリケーションの予期しないエラーをハンドルします.
     */
    @ExceptionHandler({ Error.class, RuntimeException.class })
    public final ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        return handleSystemException(ex, "UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * バリデーションエラーをハンドルします.
     */
    protected ResponseEntity<Object> handleValidationException(Exception ex, BindingResult bindingResult,
            WebRequest request) {

        List<String> causes = bindingResult.getFieldErrors().stream().map(FieldError::getField)
                .toList();
        logger.warn(ex.getMessage());
        return handleExceptionInternal(ex, "INVALID_INPUT", causes, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * ResponseEntity を作成します.
     */
    protected ResponseEntity<Object> handleSystemException(Exception ex, String code, HttpStatus statusCode,
            WebRequest request) {

        logger.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, code, Collections.emptyList(), statusCode, request);
    }

    /**
     * ResponseEntity を作成します.
     */
    protected ResponseEntity<Object> handleBusinessException(Exception ex, String code, HttpStatus statusCode,
            WebRequest request) {

        logger.error(ex.getMessage(), ex);

        return handleExceptionInternal(ex, code, Collections.emptyList(), statusCode, request);
    }

    /**
     * ResponseEntity を作成します.
     */
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, String code, List<String> causes,
            HttpStatus statusCode, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("causes", causes);

        if (isDebuggable()) {
            error.put("debug", ExceptionUtils.getStackTrace(ex));
        }

        return handleExceptionInternal(ex, error, new HttpHeaders(), statusCode, request);
    }

    protected boolean isDebuggable() {
        return debuggable;
    }
}
