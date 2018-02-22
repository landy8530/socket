package com.landy.server.job;

import com.landy.server.config.Configuration;
import com.landy.utils.SocketIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * @author landyl
 * @create 4:06 PM 02/22/2018
 */
public class Job implements Runnable {
    static Logger logger = LoggerFactory.getLogger(Job.class);
    private Socket socket;

    public Job(Socket socket){
        this.socket = socket;
    }


    public void run() {
        Properties p = Configuration.getConfig();
        String encoding = p.getProperty("socketStreamEncoding");

        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            input = SocketIO.getDataInput(socket);

            int length = input.read();
            byte[] bytes = new byte[length];
            input.readFully(bytes, 0, length); //input.read(bytes);确实取不到完整的字符串
            String user = new String(bytes,encoding).trim();
            int pwd = input.read();

            String result = null;
            if(null != user && !user.equals("") && user.equals("name") && pwd == 123){
                result = "login success";
            }else{
                result = "login failed";
            }
            logger.info("request user:"+user);
            logger.info("request pwd:"+pwd);

            output = SocketIO.getDataOutput(socket);

            bytes = result.getBytes(encoding);
            length = (short)bytes.length;
            output.writeShort(length);
            output.write(bytes);

            logger.info("response info:"+result);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("business thread run exception");
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("server socket close error");
            }
        }
    }
}
