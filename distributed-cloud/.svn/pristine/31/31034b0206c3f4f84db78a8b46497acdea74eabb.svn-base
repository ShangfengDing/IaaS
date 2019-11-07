package appcloud.distributed.header;

import appcloud.distributed.configmanager.VersionInfo;
import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class VersionInfoHeader extends ConsumerHeader{

    private String requestId;

    private VersionInfo info;

    public VersionInfoHeader() {
    }

    public VersionInfoHeader(String requestId, VersionInfo info) {
        this.requestId = requestId;
        this.info = info;
    }

    @Override
    public void checkFileds() {}
}
