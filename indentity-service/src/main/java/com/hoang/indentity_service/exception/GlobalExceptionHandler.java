package com.hoang.indentity_service.exception;

import com.hoang.indentity_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> HandlingRunTimeException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<String> apiReponse = new ApiResponse<String>();
        apiReponse.setMessage(errorCode.getMessage());
        apiReponse.setCode(errorCode.getCode());
        return ResponseEntity.
                status(errorCode.getHttpStatusCode())
                .body(apiReponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> HandlingMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String enum_key = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(enum_key);
        }catch (IllegalArgumentException ex){

        }

        ApiResponse apiReponse = new ApiResponse();
        apiReponse.setMessage(errorCode.getMessage());
        apiReponse.setCode(errorCode.getCode());
        return ResponseEntity.
                status(errorCode.getHttpStatusCode())
                .body(apiReponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse> HandlingAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse apiReponse = ApiResponse
                .builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiReponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> HandlingException(Exception e){
        e.printStackTrace();
        ApiResponse<String> apiReponse = new ApiResponse<String>();
        apiReponse.setMessage("lỗi : "+e.getMessage());
        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        apiReponse.setCode(errorCode.getCode());
        return ResponseEntity.
                status(errorCode.getHttpStatusCode())
                .body(apiReponse);
    }



}
