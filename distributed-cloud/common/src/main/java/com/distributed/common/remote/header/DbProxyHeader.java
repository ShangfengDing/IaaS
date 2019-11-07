package com.distributed.common.remote.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idan on 2017/12/12.
 */
@Data
public class DbProxyHeader extends ConsumerHeader{

    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] invokeArgs ;

    @Override
    public void checkFileds() {
        System.out.println("the interface of service is: "+interfaceName);
    }

    public DbProxyHeader() {
    }

    public DbProxyHeader(String interfaceName, String methodName, Class<?>[] parameterTypes,Object[] invokeArgs) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.invokeArgs = invokeArgs;
    }
}
