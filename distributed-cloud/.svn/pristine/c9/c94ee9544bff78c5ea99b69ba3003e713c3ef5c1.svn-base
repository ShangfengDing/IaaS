package appcloud.distributed.nativeConfig;

import appcloud.distributed.common.Constants;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lizhenhao on 2018/1/2.
 */
public class SshExector {

    protected final static Logger LOGGER = LoggerFactory.getLogger(SshExector.class);

    private final JSch jsch;

    private String username;

    private String password;

    private String ipAddress;

    private int sshPort;

    private Session session;

    public SshExector() {
        jsch = new JSch();
        username = Constants.DNS_SERVER_ROOT_NAME;
        password = Constants.DNS_SERVER_PASSWORD;
        ipAddress = Constants.DNS_SERVER_IP;
        sshPort = Constants.DNS_SSH_PORT;
//        initSession();
    }

    public void initSession() {
        try {
            session = jsch.getSession(username,ipAddress,sshPort);
            session.setPassword(password);
            session.setUserInfo(new ConfigUserInfo());
            session.connect();
        } catch (JSchException e) {
            LOGGER.error("初始化SSH session失败,错误信息：",e);
            session = null;
        }
    }

    public synchronized String executeSSHCommand(String command) throws JSchException, IOException {
        if (session == null) {
            LOGGER.error("session创建失败,重试");
            initSession();
            if (session == null) return "";
        }
        BufferedReader reader = null;
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            channel.connect();
            InputStream in = channel.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String buf = "";
            while ((buf = reader.readLine()) != null) {
                    response.append(buf + "\n");
            }
            return response.toString();
        } finally{
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.error("executeSSHCommand：IO出错",e);
            }
            channel.disconnect();
        }
    }

    public void close() {
        session.disconnect();
    }

    class ConfigUserInfo implements UserInfo {

        public String getPassphrase() {
            return null;
        }

        public String getPassword() {
            return null;
        }

        public boolean promptPassword(String s) {
            return false;
        }

        public boolean promptPassphrase(String s) {
            return false;
        }

        public boolean promptYesNo(String s) {
            if (s.contains("The authenticity of host")) {
                return true;
            }
            return false;
        }
        public void showMessage(String s) {

        }
    }

    public static void main(String[] args) throws JSchException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession("root","192.168.10.2",22);
        session.setPassword("abc123");
        session.setUserInfo(new UserInfo() {
            public String getPassphrase() {
                return null;
            }

            public String getPassword() {
                return null;
            }

            public boolean promptPassword(String s) {
                return false;
            }

            public boolean promptPassphrase(String s) {
                return false;
            }

            public boolean promptYesNo(String s) {
                System.out.println("MyUserInfo.promptYesNo()");
                System.out.println(s);
                if (s.contains("The authenticity of host")) {
                    return true;
                }
                return false;
            }

            public void showMessage(String s) {

            }
        });
        session.connect();
        BufferedReader reader = null;

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
//        String dnsContent = "zone "+"\"test.com\""+" IN {\n" +
//                "        type master;\n" +
//                "        file \"test.com.zone\";\n" +
//                "};";
//        channel.setCommand("echo "+"'"+dnsContent+"'" +">>"+ Constants.DNS_ZONES);
//        String command = "cat /etc/named.conf | grep -w named."+ domainName;
        channel.setInputStream(null);
        channel.setErrStream(System.err);

        channel.connect();
        InputStream in = channel.getInputStream();
        reader = new BufferedReader(new InputStreamReader(in));
        String buf = null;
        while ((buf = reader.readLine()) != null) {
            System.out.println(buf);
        }
        reader.close();
        channel.disconnect();
        session.disconnect();
    }

}
