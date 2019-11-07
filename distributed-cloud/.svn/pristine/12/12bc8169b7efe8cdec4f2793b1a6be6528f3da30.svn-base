package appcloud.distributed.configmanager;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Idan on 2018/1/15.
 * 每一条需要同步的操作
 * //TODO 需要修改成不仅仅是同步方法，而是具体的数据，这个相对简单
 */
@Data
public class SynInfo implements Serializable{

    private String dataCenter;
    private String service;
    private String method;
    private Object[] args;

    public SynInfo() {}

    public SynInfo(String dataCenter, String service, String method, Object arg) {
        this.dataCenter = dataCenter;
        this.service = service;
        this.method = method;
        this.args = new Object[1];
        this.args[0] = arg;
    }

    public SynInfo(String service, String method, Object[] args) {
        this.service = service;
        this.method = method;
        this.args = args;
    }



}
