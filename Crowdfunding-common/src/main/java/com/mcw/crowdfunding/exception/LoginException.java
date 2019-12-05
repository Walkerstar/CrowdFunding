package com.mcw.crowdfunding.exception;

/**
 * @author mcw 2019\11\28 0028-21:58
 */
public class LoginException extends RuntimeException{

    public LoginException() {
    }

    public LoginException(String message){
        super(message);
    }
}
