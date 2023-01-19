package com.takirahal.srfgroup.modules.user.exceptioins.advice;

import com.takirahal.srfgroup.exceptions.advice.ControllerExceptionHandler;
import com.takirahal.srfgroup.exceptions.ErrorMessage;
import com.takirahal.srfgroup.modules.user.exceptioins.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class UserControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handeleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        log.error("InvalidPasswordException = An exception have been occurred please see logging error:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ErrorMessage> handeleEmailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request) {
        log.error("EmailAlreadyUsedException = An exception have been occurred please see logging error:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountResourceException.class)
    public ResponseEntity<ErrorMessage> handeleAccountResourceException(AccountResourceException ex, WebRequest request) {
        log.error("AccountResourceException = An exception have been occurred please see logging error:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ErrorMessage> handeleUserNotActivatedException(UserNotActivatedException ex, WebRequest request) {
        log.error("UserNotActivatedException = An exception have been occurred please see logging error:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorMessage> handeleUserBlockedException(UserBlockedException ex, WebRequest request) {
        log.error("UserBlockedException = Exception to user blocked:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorMessage> handeleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        log.error("TokenRefreshException = An exception have been occurred please see logging error:  {}", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
