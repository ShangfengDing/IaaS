import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangzhi 2018-07-04 10:38
 **/
public class MonitorTest {

    private Logger LOGGER = LoggerFactory.getLogger(MonitorTest.class);

    @Test
    public void monitorTest() {
        List<String> uuidList = new ArrayList<>();
        uuidList.add("eee3af4ad1604713a2af028a029aad96");
        uuidList.add("21ed375a8de343d8a24b04a0d834866a");
        uuidList.add("dcb70f9de9af4c2daa08f374371c0a49");
        System.out.println(produceMonitor(uuidList,"21ed375a8de343d8a24b04a0d834866a"));
    }

    public String produceMonitor(List<String> uuidList, String ownUuid) {

        int minHashCode = Integer.MAX_VALUE;
        long min = Long.MAX_VALUE;
        long ownHash = ownUuid.hashCode();
        String currentRouteInfo = null;
        String minIpRouteInfo = null;
        String monitorRoute;

        for (String uuid : uuidList) {
            int hashcode = uuid.hashCode();
            LOGGER.info(hashcode+"");
            if (hashcode > ownHash) {
                LOGGER.info("大的"+uuid);
                if (hashcode - ownHash < min) {
                    min = hashcode - ownHash;
                    currentRouteInfo = uuid;
                    LOGGER.info("更新min"+min);
                }
            }
            if (hashcode < minHashCode && hashcode != ownHash) {
                minIpRouteInfo = uuid;
                minHashCode = hashcode;
                LOGGER.info("最小：" + uuid);
            }
        }
        if (min == Long.MAX_VALUE) {
            monitorRoute = minIpRouteInfo;
            LOGGER.info("监控对象为值最小" + minIpRouteInfo);
        } else {
            monitorRoute = currentRouteInfo;
        }
        LOGGER.info("monitor node change to:" + monitorRoute);
        return monitorRoute;


    }
}
