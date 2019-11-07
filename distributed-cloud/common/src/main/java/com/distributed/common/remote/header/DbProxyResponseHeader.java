package com.distributed.common.remote.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Idan on 2017/12/12.
 */
@Data
public class DbProxyResponseHeader<T> extends ConsumerHeader implements Serializable{

    private T result;

    @Override
    public void checkFileds() {
        System.out.println("the interface of service is: "+result.toString());
    }

    public DbProxyResponseHeader() {
    }

    public DbProxyResponseHeader(T result) {
        this.result = result;
    }
}
