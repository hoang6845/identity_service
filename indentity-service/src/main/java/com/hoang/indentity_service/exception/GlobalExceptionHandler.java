package com.hoang.indentity_service.exception;

import com.hoang.indentity_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.badRequest().body(apiReponse);
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
        return ResponseEntity.badRequest().body(apiReponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> HandlingException(Exception e){
        ApiResponse<String> apiReponse = new ApiResponse<String>();
        apiReponse.setMessage(e.getMessage());
        apiReponse.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
        return ResponseEntity.badRequest().body(apiReponse);
    }

}
