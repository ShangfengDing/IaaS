package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by Idan on 2018/1/17.
 */
@Data
public class DefaultHeader<T> extends ConsumerHeader {

    private String requestId;
    private T value;

    public DefaultHeader() {
    }

    public DefaultHeader(String requestId, T value) {
        this.requestId = requestId;
        this.value = value;
    }

    @Override
    public void checkFileds() {

    }
}
