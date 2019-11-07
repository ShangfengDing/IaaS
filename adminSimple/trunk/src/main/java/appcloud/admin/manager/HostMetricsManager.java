package appcloud.admin.manager;

import appcloud.admin.dao.HostMetricsDAO;
import appcloud.admin.model.HostMetrics;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BZbyr
 */
public class HostMetricsManager {
    private static final Logger logger = Logger.getLogger(HostMetricsManager.class);

    private static HostMetricsDAO hostMetricsDAO = HostMetricsDAO.getInstance();

    private static HostMetricsManager instance = new HostMetricsManager();

    private HostMetricsManager() {}

    public static HostMetricsManager getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static List<HostMetrics> findByCreatedTime(Timestamp startTime, Timestamp endTime, String uuid) {
        logger.info("find host metrics startTime=" + startTime + " endTime=" + endTime + " UUID=" + uuid);
        return hostMetricsDAO.search("uuid", uuid, startTime, endTime, false, null);
    }

//    public static void main(String[] args) {
//        Timestamp startTime = Timestamp.valueOf("2018-03-31 00:00:00");
//        Timestamp endTime = Timestamp.valueOf("2018-04-12 00:00:00");
//        System.out.println(findByCreatedTime(startTime, endTime));
//    }
}
