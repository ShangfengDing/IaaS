package appcloud.admin.manager;

import appcloud.admin.dao.AlarmHistoryDAO;
import appcloud.admin.model.AlarmHistory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouji on 2018/4/30.
 */
public class AlarmHistoryManager {

    private static final Logger logger = Logger.getLogger(HostManager.class);

    private static AlarmHistoryDAO alarmHistoryDAO = AlarmHistoryDAO.getInstance();

    private static AlarmHistoryManager instance = new AlarmHistoryManager();

    private AlarmHistoryManager() {}

    public static AlarmHistoryManager getInstance() {
        return instance;
    }

    /**
     * @param day 时间分别是一天，七天，三十天
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<AlarmHistory> findByTime(Integer day, Integer size, Integer page) {
        Long endTime = System.currentTimeMillis();
        Long startTime = endTime - Long.valueOf(day) * 60 * 60 * 24 * 1000;
        List<Object> appNames = new ArrayList<>();
        appNames.add("cloudaimonitoralert");
        appNames.add("physical-machine-monitor");
        logger.info("find alarm history startTime=" + startTime + " endTime=" + endTime);
        List<AlarmHistory> alarmHistoryList =  alarmHistoryDAO.search(appNames, startTime, endTime, false, size, page);
        return alarmHistoryList;
    }

    public static void main(String[] args) {
        Long day = Long.valueOf(30);
        logger.info(String.valueOf(day * 60 * 60 * 24 * 1000));
        logger.info(System.currentTimeMillis());
        findByTime(1, 10, 0);
    }

}
