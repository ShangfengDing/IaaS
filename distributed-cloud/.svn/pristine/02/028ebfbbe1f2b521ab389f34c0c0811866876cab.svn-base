package appcloud.distributed.header;

import appcloud.distributed.configmanager.VersionInfo;
import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class VersionHeader extends ConsumerHeader{

    private String requestId;
    private Long version;

    public VersionHeader() {
    }

    public VersionHeader(String requestId, Long version) {
        this.requestId = requestId;
        this.version = version;
    }

    @Override
    public void checkFileds() {}
}
