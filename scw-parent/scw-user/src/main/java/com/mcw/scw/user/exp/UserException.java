package com.mcw.scw.user.exp;

import com.mcw.scw.user.enums.UserExceptionEnum;

/**
 * @author mcw 2019\12\13 0013-16:27
 */
public class UserException extends RuntimeException {

    public UserException() {
    }

    public UserException(UserExceptionEnum enums) {
        super(enums.getMsg());
    }
}
