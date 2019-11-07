package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import com.distributed.common.entity.Host;
import com.distributed.common.entity.InstanceBackInfo;
import lombok.Data;

/**
 * Created by Idan on 2018/3/12.
 */
@Data
public class VmBackCheckHeader extends ConsumerHeader{

    private String requestId;
    private Host host;

    public VmBackCheckHeader() {
        super();
    }

    public VmBackCheckHeader(String requestId, Host host) {
        this.requestId = requestId;
        this.host = host;
    }

    @Override
    public void checkFileds() {

    }
}
