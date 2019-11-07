package appcloud.netty.remoting.remote;

/**
 * Created by lizhenhao on 2017/11/9.
 */
public class NettySystemConfig {

    public final static int socketSndbufSize = 65535;

    public final static int socketRcvbufSize = 65535;

    public final static int connectTimeOut = 6000;

    public final static int frameMaxLength = Integer.MAX_VALUE;

    public static int serverPort = 8118;

    public final static int semaphorePermits = 2000;

    public final static int nioLoopWorkerGroupNumber;

    static {
        int process = Runtime.getRuntime().availableProcessors()/2;
        nioLoopWorkerGroupNumber = process < 1 ? 1:process;
    }
}
