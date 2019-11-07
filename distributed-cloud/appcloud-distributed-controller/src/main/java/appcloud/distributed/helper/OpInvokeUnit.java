package appcloud.distributed.helper;

import lombok.Data;

/**
 * Created by Idan on 2018/1/17.
 */
@Data
public class OpInvokeUnit {

    private String service;
    private String factory;
    private String method;

    public OpInvokeUnit(){}

    public OpInvokeUnit(String service, String factory, String method) {
        this.service = service;
        this.factory = factory;
        this.method = method;
    }

}
