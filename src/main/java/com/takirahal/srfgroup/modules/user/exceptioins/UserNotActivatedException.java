package com.takirahal.srfgroup.modules.user.exceptioins;

public class UserNotActivatedException extends RuntimeException{
    public UserNotActivatedException(String message){ super(message); }

}
