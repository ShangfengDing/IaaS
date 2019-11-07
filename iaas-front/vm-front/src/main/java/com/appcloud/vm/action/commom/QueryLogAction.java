package com.appcloud.vm.action.commom;

import appcloud.openapi.datatype.OperateLogItem;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rain on 2016/11/17.
 */
public class QueryLogAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(QueryLogAction.class);

    private Integer userId = this.getSessionUserID();
    private AppkeyManager appkeyManager = new AppkeyManager();
    //默认北京
    private final String regionId = "BEIJING";
    private List<OperateLogItem> logResult = new ArrayList<>();

    private String btime;
    private String etime;
    private String timeasc;
    private String size;
    private String appname;

    private Integer currentpage=1;
    private Integer pagesize=20;
    private Integer lastpage;

    public String searchLog() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = null;
            if (!StringUtil.isEmpty(btime)) {
                date = sdf.parse(btime);
                btime = date.getTime() + "";
            }
            if (!StringUtil.isEmpty(etime)) {
                date = sdf.parse(etime);
                etime = date.getTime() + "";
            }

        } catch (ParseException e) {
            logger.error(e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String,String> query = new HashMap<>();
        query.put("timeasc", !StringUtil.isEmpty(timeasc) && timeasc.equals("yes")? "yes":"no");
        query.put("size",!StringUtil.isEmpty(size)? size:"100");

        query.put("userId", userId.toString());
        if (!StringUtil.isEmpty(btime)) {
            query.put("btime", btime);
        }
        if (!StringUtil.isEmpty(etime)) {
            query.put("etime", etime);
        }
        if (StringUtil.isEmpty(appname) || appname.equals("全部")) {
            List<Appkey> appkeyList = appkeyManager.getAppkeyByUserId(userId);
            for (Appkey appkey : appkeyList) {
                List<OperateLogItem> operateLogItemList = Log.QueryLog(query, appkey, regionId);
                logger.info(operateLogItemList);
                if (operateLogItemList != null) {
                    logger.info(operateLogItemList.size());
                    if(operateLogItemList.size()%pagesize==0){
                        lastpage=operateLogItemList.size()/pagesize;
                    }else{
                        lastpage=operateLogItemList.size()/pagesize+1;
                    }
                    if(pagesize*currentpage < operateLogItemList.size()) {
                        logResult = operateLogItemList.subList(pagesize*(currentpage-1),pagesize*currentpage);
                    }else{
                        logResult=operateLogItemList.subList(pagesize*(currentpage-1),operateLogItemList.size());
                    }
//                    logResult.addAll(operateLogItemList);
                    logger.info(logResult.size());
                }
            }
        } else {
            Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
            List<OperateLogItem> operateLogItemList= Log.QueryLog(query, appkey, regionId);
            if(pagesize*currentpage < operateLogItemList.size()) {
                logResult = operateLogItemList.subList(pagesize*(currentpage-1),pagesize*currentpage);
            }else{
                logResult=operateLogItemList.subList(pagesize*(currentpage-1),operateLogItemList.size()+1);
            }

        }
        return "success";
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getTimeasc() {
        return timeasc;
    }

    public void setTimeasc(String timeasc) {
        this.timeasc = timeasc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getRegionId() {
        return regionId;
    }

    public List<OperateLogItem> getLogResult() {
        return logResult;
    }

    public void setLogResult(List<OperateLogItem> logResult) {
        this.logResult = logResult;
    }

    public Integer getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(Integer currentpage) {
        this.currentpage = currentpage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getLastpage() {
        return lastpage;
    }

    public void setLastpage(Integer lastpage) {
        this.lastpage = lastpage;
    }

    @Override
    public String toString() {
        return "QueryLogAction{" +
                "btime='" + btime + '\'' +
                ", etime='" + etime + '\'' +
                ", timeasc='" + timeasc + '\'' +
                ", size='" + size + '\'' +
                ", appname='" + appname + '\'' +
                '}';
    }
}
