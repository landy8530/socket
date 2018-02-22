package com.landy.server.auth;

import com.landy.server.config.Configuration;
import com.landy.utils.IOUtil;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Properties;

/**
 * @author landyl
 * @create 4:03 PM 02/22/2018
 */
public class Auth {

    private static SSLContext sslContext;

    public static SSLContext getSSLContext() throws Exception{
        Properties p = Configuration.getConfig();
        String protocol = p.getProperty("protocol");
        String serverCer = p.getProperty("serverCer");
        String serverCerPwd = p.getProperty("serverCerPwd");
        String serverKeyPwd = p.getProperty("serverKeyPwd");

        //Key Stroe
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(IOUtil.getInputStreamByPath(serverCer),
                serverCerPwd.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, serverKeyPwd.toCharArray());
        KeyManager[] kms = keyManagerFactory.getKeyManagers();

        TrustManager[] tms = null;
        if(Configuration.getConfig().getProperty("authority").equals("2")){
            String serverTrustCer = p.getProperty("serverTrustCer");
            String serverTrustCerPwd = p.getProperty("serverTrustCerPwd");

            //Trust Key Store
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(IOUtil.getInputStreamByPath(serverTrustCer),
                    serverTrustCerPwd.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            tms = trustManagerFactory.getTrustManagers();
        }
        sslContext = SSLContext.getInstance(protocol);
        sslContext.init(kms, tms, null);

        return sslContext;
    }

}
