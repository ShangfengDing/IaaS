package com.distributed.common.remote.header;

import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by zouji on 2018/1/9.
 */
@Data
public class RPCHeader extends ConsumerHeader {
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] invokeArgs ;

    @Override
    public void checkFileds() {
        System.out.println("the interface of service is: "+interfaceName);
    }

    public RPCHeader() {
    }

    public RPCHeader(String interfaceName, String methodName, Class<?>[] parameterTypes, Object[] invokeArgs) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.invokeArgs = invokeArgs;
    }
}
