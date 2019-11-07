package com.distributed.common.remote.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zouji on 2018/1/11.
 */
@Data
public class RPCResponseHeader extends ConsumerHeader implements Serializable{
    private Object result;

    @Override
    public void checkFileds() {
        System.out.println("the interface of service is: "+result.toString());
    }

    public RPCResponseHeader() {
    }

    public RPCResponseHeader(Object result) {
        this.result = result;
    }
}
