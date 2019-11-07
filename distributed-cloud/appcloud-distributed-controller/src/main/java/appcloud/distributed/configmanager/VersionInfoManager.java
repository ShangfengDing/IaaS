package appcloud.distributed.configmanager;


import appcloud.distributed.common.Constants;
import appcloud.distributed.helper.VersionHelper;
import appcloud.distributed.util.FileUtil;
import appcloud.distributed.util.VersionUtil;
import com.distributed.common.utils.JsonUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.common.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Idan on 2018/1/14.
 * 1. 部分存在内存里
 * 2. 如果满了则会存在日志文件中
 * //TODO 并发问题的考虑
 */
public class VersionInfoManager {
    private static final Logger logger = LoggerFactory.getLogger(VersionInfoManager.class);

    private static final LinkedBlockingDeque<VersionInfo> versionQueue = new LinkedBlockingDeque<VersionInfo>();
    private RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();

    private static VersionInfoManager versionInfoManager = new VersionInfoManager();
    public static VersionInfoManager getInstance() {
        return versionInfoManager;
    }

    /**
     * 初始化日志，可以继承后自定义一些属性
     */
    protected void init() {
    }

    /**
     * 添加日志，只添加一条SynInfo，或者多条日志
     */
    public VersionInfo addOne(SynInfo synInfo) {
        List<SynInfo> synInfos = new ArrayList<SynInfo>();
        synInfos.add(synInfo);
        VersionInfo result = add(synInfos);
        return result;
    }

    public VersionInfo add(List<SynInfo> synInfos) {
        VersionInfo latestInfo = getLatest();
        long version;
        if (latestInfo == null) {
            version = VersionUtil.getNewVersion(null);
        } else {
            version = VersionUtil.getNewVersion(latestInfo.getVersionNum());
        }

        // 如果队列满了，将会store到日志中
        VersionInfo newInfo = new VersionInfo(version, synInfos);
        addVersion(newInfo);
        return newInfo;
    }

    public void addVersion(VersionInfo versionInfo) {
        versionQueue.offer(versionInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (versionQueue.size() >= Constants.VERSION_QUEUE_SIZE) {
                    VersionInfo storeInfo = versionQueue.peekFirst();
                    try {
                        storeVersionToLog(storeInfo);
                        versionQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 修改版本日志
     */
    public Boolean modify() {
        return false;
    }

    /**
     * 删除日志
     */
    public Boolean remove(String version) {
        return false;
    }

    /**
     * 全部清除
     */
    public void clear() {
        versionQueue.clear();
    }

    /**
     * 获取最新的版本
     */
    public VersionInfo getLatest() {
        if (versionQueue.isEmpty()) {
            return null;
        }
        //TODO 获得最新版本
        VersionInfo info = versionQueue.peekLast();
        return info;
    }

    public Long getLatestVersionNum() {
        VersionInfo info = getLatest();
        if (ModelUtil.isEmpty(info)) {
            return 0L;
        } else {
            return info.getVersionNum();
        }
    }

    /**
     * 两个日志之间的比较
     * 能够根据VersionInfo判断，也可以根据Version判断
     * getAllByNum能根据相差几个版本拿到list
     */
    public List<VersionInfo> getAllByNum(Integer diffNum) {
        List<VersionInfo> versionInfoList = new ArrayList<>();
        if (diffNum <= Constants.VERSION_QUEUE_SIZE) {
            int vNum = 0;
            List<VersionInfo> tempList = new ArrayList<>();
            for (VersionInfo versionInfo : versionQueue) {
                tempList.add(versionInfo);
            }
            int size = tempList.size();
            for (int j=size-diffNum; j<size;j++) {
                versionInfoList.add(tempList.get(j));
            }
        } else {  // 需要先从文件中拿到数据，然后再从队列中拿到数据
            int leaveNum = diffNum - Constants.VERSION_QUEUE_SIZE;
            String filePath = Constants.VERSION_PATH + File.separator + Constants.VERSION_FILE_NAME;
            //倒着读取
            List<String> versions = FileUtil.getReverseFileLines(filePath, leaveNum);
            int size = versions.size();
            for (int i=size-1; i>=0; i--) {
                String version = versions.get(i);
                VersionInfo versionInfo = JsonUtil.gsonToObject(version, VersionInfo.class);
                versionInfoList.add(versionInfo);
            }

            for (VersionInfo versionInfo : versionQueue) {
                versionInfoList.add(versionInfo);
            }

        }
        return versionInfoList;
    }

    public List<VersionInfo> getByInfo(VersionInfo info) {
        Integer num = compare(info);
        return getAllByNum(num);
    }

    public List<VersionInfo> getByVersion(Long versionNum, Long selfVersionNum) {
        Integer num = VersionUtil.compare(versionNum, selfVersionNum);
        return getAllByNum(num);
    }

    public Integer compare(VersionInfo info) {
        VersionInfo latestInfo = getLatest();
        Long versionNum = info.getVersionNum();
        Long latestVersionNum = latestInfo==null?0:latestInfo.getVersionNum();
        Boolean rs = VersionUtil.isEqual(versionNum, latestVersionNum);
        if (rs) {
            return 0;
        } else {
            Integer num = VersionUtil.compare(versionNum, latestVersionNum);
            return num;
        }
    }

    /**
     * 6. 存在日志文件中
     */
    public void storeVersionToLog(VersionInfo versionInfo) {
        //TODO 存储文件
        String value = JsonUtil.jacksonToString(versionInfo);
        Boolean rs = FileUtil.store(value, Constants.VERSION_PATH, Constants.VERSION_FILE_NAME);
        logger.info(versionInfo.toString());
    }

    /**
     * 7. 获得存储文件中的日志
     * // TODO 2017/12/24 暂时不管
     */
    public List<VersionInfo> getVersionByLog() {
        // 从日志文件中读取
        return null;
    }

    /**
     * 8. 根据version执行同步操作
     * 这里是同步一条数据
     */
    public Boolean synInfo(VersionInfo versionInfo) {
        List<VersionInfo> tempList = new ArrayList<>();
        tempList.add(versionInfo);
        return synInfo(tempList);
    }

    /**
     * 这里是同步多条数据
     * 同步数据时，如果判断在本地
     * @param versionInfos
     * @return
     */
    public Boolean synInfo(List<VersionInfo> versionInfos) {
        logger.info("不管是否会执行recovery命令，不都将新的版本添加到队列中，开始必须添加到队列中……");
        for (VersionInfo versionInfo : versionInfos) {
            List<SynInfo> synInfoList = versionInfo.getSynInfos();
            versionInfoManager.addVersion(versionInfo);
            logger.info("开始同步恢复本地的数据……");
            for (SynInfo info : synInfoList) {
                logger.info("info: "+info.toString());
                recoveryByInfo(info);
            }
            logger.info("同步恢复本地的数据结束……");
        }
        return true;
    }

    public Object recoveryByInfo(SynInfo synInfo) {
        String dataCenter = synInfo.getDataCenter();
        String ownDataCenter = routeInfoManager.getOwnDataCenter();
        if (dataCenter.equals(ownDataCenter)) {
            return null;
        }

        String requestPath = synInfo.getService();
        String requestMethod = synInfo.getMethod();
        Handler handler = VersionHelper.getHandler(requestPath, requestMethod);
        Object[] args = synInfo.getArgs();
        ServiceHandler serviceHandler = (ServiceHandler) handler.getInstance();
        try {
            Object instance = serviceHandler.getMethod().invoke(serviceHandler.getFactoryClass());
            Object result = ReflectionUtil.invokeMethod(instance, handler.getMethod(), args);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
