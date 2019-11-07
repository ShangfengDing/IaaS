package appcloud.netty.remoting.exception;

/**
 * Created by lizhenhao on 2017/11/7.
 */
public class RemotingTimeOutException extends Exception {

    public String addr;

    public RemotingTimeOutException() {
    }


    public RemotingTimeOutException(String addr) {
        super();
        this.addr = addr;
    }

    public RemotingTimeOutException(String message,String addr) {
        super(message);
        this.addr = addr;
    }

    public RemotingTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemotingTimeOutException(Throwable cause) {
        super(cause);
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
