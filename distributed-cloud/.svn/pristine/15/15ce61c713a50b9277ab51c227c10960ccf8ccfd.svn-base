package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class VersionRefreshHeader extends ConsumerHeader{

    private String requestId;
    private Long versionNum;
    private String addr;

    public VersionRefreshHeader() {
    }

    public VersionRefreshHeader(String requestId, Long versionNum, String addr) {
        this.requestId = requestId;
        this.versionNum = versionNum;
        this.addr = addr;
    }

    @Override
    public void checkFileds() {}
}
