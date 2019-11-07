package appcloud.netty.remoting.common;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public class RequestCode {

    public final static int REGISTER_BROKER = 103;
    public final static int DISPATCH_REGISTER_MESSAGE = 104;
    public final static int HEART_BEAT = 105;
    public final static int BROKER_DOWN = 106;
    public final static int DB_PROXY_REQUEST = 107;

    /**
     * 备份相关的code，以330开头
     */
    public final static int READY_IMAGE_BACK=331;
    public final static int CHECK_BACKUP_DEST=332;
    public final static int SEND_IMAGE_BACK=333;
    /**
     * 账户操作的code，以15开头
     */
    public final static int USER_CREATE=151;

    /**
     * 版本控制相关
     */
    public final static int VERSION_DISPATCH=251;
    public final static int VERSION_SYN=252;
    public final static int VERSION_REFRESH=253;


    public final static int RPC_REQUEST = 120;

    public final static int CHECK_CONNECTION = 303;

    public final static int SYSTEM_ERROR = 500;
    public final static int SUCCESS = 200;
    public final static int DEFAULT = 201;

}
