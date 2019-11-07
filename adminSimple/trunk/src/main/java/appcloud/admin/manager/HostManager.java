package appcloud.admin.manager;

import appcloud.admin.dao.HostDAO;
import appcloud.admin.model.Host;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BZbyr
 */
public class HostManager {
    private static final Logger logger = Logger.getLogger(HostManager.class);

    private static HostDAO hostDAO = HostDAO.getInstance();

    private static HostManager instance = new HostManager();

    private HostManager() {}

    public static HostManager getInstance() {
        return instance;
    }

    public static List<Host> findByCreatedTime(Timestamp startTime, Timestamp endTime) {
        return hostDAO.search(null, null, startTime, endTime, true,1);
    }

//    public static void main(String[] args) {
//        Timestamp startTime = Timestamp.valueOf("2018-03-31 00:00:00");
//        Timestamp endTime = Timestamp.valueOf("2018-04-12 00:00:00");
//        System.out.println(findByCreatedTime(startTime, endTime));
//    }
}
