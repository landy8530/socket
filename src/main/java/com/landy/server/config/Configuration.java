package com.landy.server.config;




import com.landy.utils.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author landyl
 * @create 3:52 PM 02/22/2018
 */
public class Configuration {

    private static Properties config;

    static Logger logger = LoggerFactory.getLogger(Configuration.class);

    public static Properties getConfig(){
        try{
            if(null == config){
                logger.info("load config file....");
                InputStream input = IOUtil.getInputStreamByPath("conf/conf.properties"); /*Configuration.class.getClassLoader()
                        .getResourceAsStream("conf/conf.properties");*/
//                File configFile = new File("conf/conf.properties");
//                if(configFile.exists() && configFile.isFile()
//                        && configFile.canRead()){


//                    InputStream input = new FileInputStream(configFile);
                    config = new Properties();
                    config.load(input);
//                }
            }
        }catch(Exception e){
            //default set
            config = new Properties();
            config.setProperty("protocol", "TLSV1");
            config.setProperty("serverCer", "certificate/server.jks");
            config.setProperty("serverCerPwd", "1234sp");
            config.setProperty("serverKeyPwd", "1234kp");
            config.setProperty("serverTrustCer", "certificate/serverTrust.jks");
            config.setProperty("serverTrustCerPwd", "1234sp");
            config.setProperty("clientCer", "certificate/client.jks");
            config.setProperty("clientCerPwd", "1234sp");
            config.setProperty("clientKeyPwd", "1234kp");
            config.setProperty("clientTrustCer", "certificate/clientTrust.jks");
            config.setProperty("clientTrustCerPwd", "1234sp");
            config.setProperty("serverListenPort", "10000");
            config.setProperty("serverThreadPoolSize", "5");
            config.setProperty("serverRequestQueueSize", "10");
            config.setProperty("socketStreamEncoding", "UTF-8");
        }
        return config;
    }

}
