package com.takirahal.srfgroup.modules.user.exceptioins;

public class TokenRefreshException  extends RuntimeException{
    public TokenRefreshException(String message) {
        super(message);
    }
}
