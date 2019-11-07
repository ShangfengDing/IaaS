package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;

/**
 * Created by lizhenhao on 2017/11/25.
 */
public class HeartBeatHeader extends ConsumerHeader {

    private long timemillions;

    public HeartBeatHeader() {
    }

    public HeartBeatHeader(long timemillions) {
        this.timemillions = timemillions;
    }

    public long getTimemillions() {
        return timemillions;
    }

    public void setTimemillions(long timemillions) {
        this.timemillions = timemillions;
    }

    public void checkFileds() {

    }
}
