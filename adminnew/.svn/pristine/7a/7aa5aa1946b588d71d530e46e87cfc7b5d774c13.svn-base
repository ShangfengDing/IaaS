package appcloud.admin.manager;

import appcloud.admin.dao.HostDetailDAO;
import appcloud.admin.model.HostDetail;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *
 * @author BZbyr
 */
public class HostDetailManager {
    private static final Logger logger = Logger.getLogger(HostDetailManager.class);

    private static HostDetailDAO hostDetailDAO = HostDetailDAO.getInstance();

    private static HostDetailManager instance = new HostDetailManager();

    private HostDetailManager() {}

    public static HostDetailManager getInstance() {
        return instance;
    }

    public static List<HostDetail> findByIp(String ipaddress) {
        return hostDetailDAO.findByProperty("ipaddress", ipaddress);
    }

//    public static void main(String[] args) {
//        Timestamp startTime = Timestamp.valueOf("2018-03-31 00:00:00");
//        Timestamp endTime = Timestamp.valueOf("2018-04-12 00:00:00");
//        System.out.println(findByCreatedTime(startTime, endTime));
//    }
}
