package appcloud.distributed.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import com.distributed.common.entity.VmBack;
import lombok.Data;

/**
 * Created by lizhenhao on 2017/12/16.
 */
@Data
public class TransportFileHeader extends ConsumerHeader{

    /**
     * 储存数据一些参数，就在结束后才能使用
     */
    private VmBack vmBack;

    /**
     * 传输文件路径
     */
    private String filePath;

    /**
     * 是否传输结束
     */
    private boolean transportEnd;

    /**
     * 这一次的写入位置
     */
    private long position;

    public TransportFileHeader() {
    }

    public TransportFileHeader(String filePath, boolean transportEnd,long position) {
        this.filePath = filePath;
        this.transportEnd = transportEnd;
        this.position = position;
    }

    public TransportFileHeader(VmBack vmBack, String filePath, boolean transportEnd, long position) {
        this.vmBack = vmBack;
        this.filePath = filePath;
        this.transportEnd = transportEnd;
        this.position = position;
    }

    @Override
    public void checkFileds() {

    }
}
