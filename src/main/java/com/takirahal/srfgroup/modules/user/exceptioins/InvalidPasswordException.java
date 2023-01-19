package com.takirahal.srfgroup.modules.user.exceptioins;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
