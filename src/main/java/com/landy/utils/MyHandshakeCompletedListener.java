package com.landy.utils;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;

/**
 * @author landyl
 * @create 4:01 PM 02/22/2018
 */
public class MyHandshakeCompletedListener implements HandshakeCompletedListener {
    public void handshakeCompleted(HandshakeCompletedEvent arg0) {
        System.out.println("Handshake finished successfully");
    }
}
