package com.landy.client;

import com.landy.client.auth.Auth;
import com.landy.server.config.Configuration;
import com.landy.utils.MyHandshakeCompletedListener;
import com.landy.utils.SocketIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Properties;

/**
 * @author landyl
 * @create 3:59 PM 02/22/2018
 */
public class Client {
    static Logger logger = LoggerFactory.getLogger(Client.class);
    private SSLContext sslContext;
    private String host = "127.0.0.1";
    private SSLSocket socket;
    private Properties p;

    public Client(){
        try {
            p = Configuration.getConfig();
            Integer port = Integer.valueOf(p.getProperty("serverListenPort"));
            sslContext = Auth.getSSLContext();
            SSLSocketFactory factory = (SSLSocketFactory) sslContext.getSocketFactory();
            socket = (SSLSocket)factory.createSocket();
            String[] pwdsuits = socket.getSupportedCipherSuites();
            //socket可以使用所有支持的加密套件
            socket.setEnabledCipherSuites(pwdsuits);
            //默认就是true
            socket.setUseClientMode(true);

            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address, 0);

            MyHandshakeCompletedListener listener = new MyHandshakeCompletedListener();
            socket.addHandshakeCompletedListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("socket establish failed!");
        }
    }

    public void request(){
        try{
            String encoding = p.getProperty("socketStreamEncoding");

            DataOutputStream output = SocketIO.getDataOutput(socket);
            String user = "name";
            byte[] bytes = user.getBytes(encoding);
            int length = bytes.length;
            int pwd = 123;


            output.write(length);
            output.write(bytes);
            output.write(pwd);

            logger.info("request length :"+ length);
            logger.info("request user :"+ user);
            logger.info("request pwd :"+ pwd);

            logger.info("request bytes:"+new String(bytes,encoding));


            DataInputStream input = SocketIO.getDataInput(socket);
            length = input.readShort();
            bytes = new byte[length];
            input.read(bytes);

            logger.info("request result:"+new String(bytes,encoding));
        }catch(Exception e){
            e.printStackTrace();
            logger.error("request error");
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Client client = new Client();
        client.request();
    }


}
