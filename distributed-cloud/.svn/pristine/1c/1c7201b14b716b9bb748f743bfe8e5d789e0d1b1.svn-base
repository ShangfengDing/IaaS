package appcloud.distributed.configmanager;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Created by Idan on 2018/3/20.
 */
@Data
public class ServiceHandler {

    private Class<?> factoryClass;
    private Method method;

    public ServiceHandler() {
    }

    public ServiceHandler(Class<?> factoryClass, Method method) {
        this.factoryClass = factoryClass;
        this.method = method;
    }
}
