package com.nilCux.backRacine.modules.exception;

import com.nilCux.backRacine.modules.enumeration.ResultCode;
import com.nilCux.backRacine.modules.output.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.security.AccessControlException;


@Slf4j
@RestControllerAdvice
public class CustomizedGlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizedGlobalExceptionHandler.class);
    
    @ExceptionHandler(value = CustomizedException.class)
    public ApiResult myException(CustomizedException e) {
        log.error("CustomizedException：", e);
        if(e.getCode() != null){
            return ApiResult.fail(e.getCode(), e.getMessage());
        }
        return ApiResult.fail( e.getMessage() );
    }


    /**
     * Argument Invalid Exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e ) {
        log.error( "Invalid Argument Exception::" + e.getMessage(), e );
        return ApiResult.fail( e.getBindingResult().getFieldError().getDefaultMessage() );
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResult handleValidationException(ValidationException e) {
        log.error( "ValidationException:", e );
        return ApiResult.fail( e.getCause().getMessage() );
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error( "ValidationException:" + e.getMessage(), e );
        return ApiResult.fail( e.getMessage() );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult handlerNoFoundException(Exception e) {
        return ApiResult.fail( 404, "None Handler Found" );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResult handleDuplicateKeyException(DuplicateKeyException e) {
        return ApiResult.fail( "Duplicate Key Exception" );
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResult handleRuntimeException(RuntimeException e) {
        LOG.error("RuntimeException:", e);
        return ApiResult.fail("RuntimeException");
    }

    /**
     * NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResult nullPointerExceptionHandler(NullPointerException e) {
        log.error("NullPointerException:", e);
        return ApiResult.fail("NullPointerException");
    }

    /**
     * ClassCastException
     */
    @ExceptionHandler(ClassCastException.class)
    public ApiResult classCastExceptionHandler(ClassCastException e) {
        log.error("ClassCastException:", e);
        return ApiResult.fail("ClassCastException!");
    }

    /**
     * ArrayIndexOutOfBoundsException
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ApiResult ArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        log.error("ArrayIndexOutOfBoundsException:", e);
        return ApiResult.fail("ArrayIndexOutOfBoundsException!");
    }

    /**
     * Exception
     */
    @ExceptionHandler({Exception.class})
    public ApiResult exception(Exception e) {
        log.error("Exception:", e);
        return ApiResult.fail( 500, "Exception："+ e);
    }

    @ExceptionHandler(AccessControlException.class)
    public ApiResult AccessDeniedHandler(AccessDeniedException e) {
        return ApiResult.fail(ResultCode.ACCESS_DENIED.getCode(),e.getMessage());
    }

}
