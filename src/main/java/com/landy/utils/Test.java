package com.landy.utils;

import java.io.DataInputStream;

/**
 * @author landyl
 * @create 4:49 PM 02/22/2018
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String user = "name你好";
        String encoding = "UTF-8";//"ISO8859-1";
        byte[] bytes = user.getBytes(encoding);
        int length = bytes.length;


        System.out.println(length);

        String s = new String(bytes,encoding);

        System.out.println(s);


    }
}
