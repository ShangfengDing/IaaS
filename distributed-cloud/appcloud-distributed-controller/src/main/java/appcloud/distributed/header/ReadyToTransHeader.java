package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by Idan on 2018/1/5.
 */
@Data
public class ReadyToTransHeader extends ConsumerHeader {


    private String requestId;
    private String instanceUuid;
    private String diskId;
    private boolean needToClean;
    private String appkeyId;
    private String appkeySecret;
    private String userEmail;
    private String accountName;
    private String destDataCenter;

    public ReadyToTransHeader(String requestId, String instanceUuid, String diskId, boolean needToClean, String appkeyId, String appkeySecret, String userEmail,String accountName, String destDataCenter) {
        this.requestId = requestId;
        this.instanceUuid = instanceUuid;
        this.diskId = diskId;
        this.needToClean = needToClean;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.userEmail = userEmail;
        this.accountName = accountName;
        this.destDataCenter = destDataCenter;
    }

    public void checkFileds() {

    }
}
