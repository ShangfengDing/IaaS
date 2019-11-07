package appcloud.distributed.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Idan on 2018/1/15.
 * 注解分为三部分，接口，方法名，参数在方法中的属性值
 * 需要同步的方法上，需要标注这个注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SynMethod {

    String[] value();

}
