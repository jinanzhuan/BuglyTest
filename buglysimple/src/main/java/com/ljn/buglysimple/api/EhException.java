package com.ljn.buglysimple.api;

import java.io.IOException;

/**
 * Created by songyuqiang on 16/11/3.
 */
public class EhException extends IOException {


    public EhException(String errmsg) {
        super(errmsg);
    }
}
