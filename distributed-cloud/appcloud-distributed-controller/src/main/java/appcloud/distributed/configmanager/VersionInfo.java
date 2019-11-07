package appcloud.distributed.configmanager;

import appcloud.distributed.util.TimeUtil;
import lombok.Data;

import java.util.List;


/**
 * Created by Idan on 2018/1/14.
 * 每一条版本日志
 */
@Data
public class VersionInfo {

    /**
     * version 是版本号
     * synInfos： 每一版本具体的日志
     */
    private long versionNum;
    private long createStamp;
    private List<SynInfo> synInfos;

    public VersionInfo() {}

    public VersionInfo(long versionNum, List<SynInfo> synInfos) {
        this.versionNum = versionNum;
        this.createStamp = TimeUtil.getCurrentTimestamp().getTime();
        this.synInfos = synInfos;
    }

    public VersionInfo(long versionNum, long createStamp, List<SynInfo> synInfos) {
        this.versionNum = versionNum;
        this.createStamp = createStamp;
        this.synInfos = synInfos;
    }

}

