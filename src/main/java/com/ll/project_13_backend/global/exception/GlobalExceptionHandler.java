package com.ll.project_13_backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponseDto> validException(MethodArgumentNotValidException e) {
//        log.error(e.getMessage(), e);
//        BindingResult bindingResult = e.getBindingResult();
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorCode.INVALID_INPUT_VALUE, bindingResult);
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ErrorResponseDto> typeMismatchException(MethodArgumentTypeMismatchException e) {
//        log.error(e.getMessage(), e);
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(e);
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<ErrorResponseDto> businessException(BusinessException e) {
//        log.error(e.getMessage(), e);
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(e.getErrorCode());
//        return new ResponseEntity<>(errorResponseDto, HttpStatusCode.valueOf(e.getErrorCode().getStatus()));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
//        log.error(e.getMessage(), e);
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorCode.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
