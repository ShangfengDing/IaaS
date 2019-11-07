package appcloud.distributed.process.operate;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.Transport.MappedReadFile;
import com.distributed.common.entity.VmBack;

/**
 * Created by lizhenhao on 2018/1/5.
 */
public interface TransPortOperate {

    /**
     * 传输文件
     * @param filePath 源镜像的路径， 形式 /nfs/192.168.1.24/volume……
     * @param destAddress 目标数据中心的地址
     * @param needToClean 是否需要清空
     * @param vmBack vm_backup数据库的数据。该数据是需要存储在源数据中心和备份数据中心的，其中已经有
     */
    public void transportImageBack(String filePath, String destAddress, boolean needToClean, VmBack vmBack);

    @Deprecated
    public void transportImageBack(String filePath, String destAddress, boolean needToClean, VmBack vmBack, CloudControllerClientWapper clientWapper);

    public boolean handlerTransPortException(MappedReadFile mappedFile, String destAddress, byte[] message, VmBack vmBack);
}
