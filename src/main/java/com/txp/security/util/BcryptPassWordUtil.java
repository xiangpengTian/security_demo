package com.txp.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPassWordUtil {

    public static String passwordByBcrypt(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
