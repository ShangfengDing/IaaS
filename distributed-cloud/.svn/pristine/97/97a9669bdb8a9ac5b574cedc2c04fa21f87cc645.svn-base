package appcloud.netty.remoting.exception;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public class RemotingConnectionException extends Exception {

    public String addr;

    public RemotingConnectionException(String addr) {
        super();
        this.addr = addr;
    }

    public RemotingConnectionException(String message,String addr) {
        super(message);
        this.addr = addr;
    }

    public RemotingConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemotingConnectionException(Throwable cause) {
        super(cause);
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
