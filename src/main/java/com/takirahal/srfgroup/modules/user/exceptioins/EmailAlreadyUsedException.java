package com.takirahal.srfgroup.modules.user.exceptioins;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String message){super(message);}
}
