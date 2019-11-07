package appcloud.distributed.configmanager;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class Handler {

    private Class<?> serviceClass;
    private Method method;
    private Object instance;

    public Handler() {}

    public Handler(Class<?> serviceClass, Method method, Object instance) {
        this.serviceClass = serviceClass;
        this.method = method;
        this.instance = instance;
    }
}
