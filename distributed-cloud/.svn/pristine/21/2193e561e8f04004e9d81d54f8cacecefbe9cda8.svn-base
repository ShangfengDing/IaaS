package appcloud.distributed.header;

import appcloud.distributed.configmanager.VersionInfo;
import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

import java.util.List;

/**
 * Created by Idan on 2018/1/15.
 * 版本不同时，需要返回这个不同
 */
@Data
public class VersionInfoListHeader extends ConsumerHeader{

    private List<VersionInfo> infoList;

    public VersionInfoListHeader() {
    }

    public VersionInfoListHeader(List<VersionInfo> infoList) {
        this.infoList = infoList;
    }

    @Override
    public void checkFileds() {}
}
