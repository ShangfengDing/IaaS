package appcloud.nodeagent.monitor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Idan on 2018/8/3.
 */

@Component
public class ServiceHMonitor {

    public ServiceHMonitor() {
        System.out.println("construct haha");
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void serviceMonitor() {
        //TODO 发送hbase
        String hello = "hala";
        System.out.println("this is a start ……");
    }

}
