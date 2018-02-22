package com.landy.utils;

import java.io.InputStream;

/**
 * @author landyl
 * @create 4:27 PM 02/22/2018
 */
public class IOUtil {

    public static InputStream getInputStreamByPath(String path) {

        InputStream input ;
        input = IOUtil.class.getResourceAsStream(path);
        if(null == input) {
            input = IOUtil.class.getClassLoader().getResourceAsStream(path);
        }
        return input;
    }

}
