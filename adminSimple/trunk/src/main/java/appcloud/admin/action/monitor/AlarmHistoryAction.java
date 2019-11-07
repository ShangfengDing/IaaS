package appcloud.admin.action.monitor;

import appcloud.admin.dao.AlarmDAO;
import appcloud.admin.dao.AlarmHistoryDAO;
import appcloud.admin.manager.AlarmHistoryManager;
import appcloud.admin.model.Alarm;
import appcloud.admin.model.AlarmHistory;
import appcloud.admin.model.AlarmStatus;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouji on 2018/5/1.
 */
public class AlarmHistoryAction extends ActionSupport {
    private Logger logger = Logger.getLogger(AlarmHistoryAction.class);

    private Integer endPage;
    private Integer day;
    private Integer size = 20;
    private List<AlarmHistory> alarmHistoryList;
    private Map<Long, String> timestampMap = new HashMap<>();
    private Map<Integer, String> alarmStatusMap = new HashMap<>();
    private String btime;
    private String etime;
    private AlarmHistoryDAO alarmHistoryDAO=AlarmHistoryDAO.getInstance();

    private Integer page;
    private long count;

    private List<AlarmHistory> alarmArray=new ArrayList<>();
    private Map<Integer,String> dateMap=new HashMap<>();

    private Integer id;
    private String status;
    private String comment;
    private String result;
    private long num;

    public String findAlarmHistory() {
        alarmHistoryList = AlarmHistoryManager.findByTime(day, size, endPage);
        AlarmDAO alarmDAO = AlarmDAO.getInstance();
        for (AlarmHistory alarmHistory : alarmHistoryList) {
            Timestamp alarmTime = new Timestamp(alarmHistory.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timestampMap.put(alarmHistory.getTime(), sdf.format(alarmTime));

            List<Alarm> alarmList = alarmDAO.findByProperty("alarmId", alarmHistory.getId());
            if (alarmList == null || alarmList.size() == 0) {
                alarmStatusMap.put(alarmHistory.getId(), "待处理");
                Alarm alarm = new Alarm(alarmHistory.getId(), AlarmStatus.SUSPENDING);
                alarmDAO.save(alarm);
            } else {
                AlarmStatus alarmStatus = alarmList.get(0).getStatus();
                logger.info(alarmStatus);
                if (alarmStatus == AlarmStatus.SUSPENDING) {
                    alarmStatusMap.put(alarmHistory.getId(), "待处理");
                } else if (alarmStatus == AlarmStatus.IGNORED) {
                    alarmStatusMap.put(alarmHistory.getId(), "已忽略");
                } else {
                    alarmStatusMap.put(alarmHistory.getId(), "已解决");
                }
            }
        }
        return SUCCESS;
    }

    public String findPeriodHistory(){
        List<Object> values=new ArrayList<>();
        values.add("test");
        if(page == 0 && ActionContext.getContext().getSession().containsKey("currentPage")){
            page = (Integer) ActionContext.getContext().getSession().get("currentPage");
        }
        ActionContext.getContext().getSession().remove("currentPage");
        Long begin,end;
        try{
           begin= Long.parseLong(btime);
        }catch(Exception e){
            begin=null;
        }
        try{
            end=Long.parseLong(etime);
        }catch(Exception e){
            end=null;
        }
        count=alarmHistoryDAO.count(values,begin,end);
        if(count%this.size > 0) {
            this.endPage = (int) (count/this.size + 1);
        }
        else {
            this.endPage = (int) (count/this.size);
        }
        DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        alarmHistoryList = alarmHistoryDAO.search(values,begin,end,Boolean.TRUE,size,page);
        for(AlarmHistory ah:alarmHistoryList){
            dateMap.put(ah.getId(),sdf.format(ah.getTime()));
        }
        return Action.SUCCESS;
    }

    public String changeStatus(){
        AlarmHistory alarmHistory=alarmHistoryDAO.findById(id);
        alarmHistory.setStatus(status);
        try{
            alarmHistoryDAO.update(alarmHistory);
        }catch(Exception e){
            logger.error("update status error");
            e.printStackTrace();
            return Action.ERROR;
        }
        result="success";
        return Action.SUCCESS;
    }

    public String addComment(){
        AlarmHistory alarmHistory=alarmHistoryDAO.findById(id);
        alarmHistory.setComment(comment);
        try{
            alarmHistoryDAO.update(alarmHistory);
        }catch(Exception e){
            logger.error("update comment error");
            e.printStackTrace();
            result="error";
            return Action.ERROR;
        }
        result="success";
        return Action.SUCCESS;
    }

    public String countUntreated(){
        List<Object> values=new ArrayList<>();
        values.add("test");
        Map<String,List<Object>> map=new HashMap<String, List<Object>>();
        map.put("appName",values);
        Map<String,Object> params=new HashMap<>();
        params.put("status","UNTREATED");
        num=alarmHistoryDAO.countByProperty(params,map);
        return Action.SUCCESS;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<AlarmHistory> getAlarmHistoryList() {
        return alarmHistoryList;
    }

    public void setAlarmHistoryList(List<AlarmHistory> alarmHistoryList) {
        this.alarmHistoryList = alarmHistoryList;
    }

    public Map<Long, String> getTimestampMap() {
        return timestampMap;
    }

    public void setTimestampMap(Map<Long, String> timestampMap) {
        this.timestampMap = timestampMap;
    }

    public Map<Integer, String> getAlarmStatusMap() {
        return alarmStatusMap;
    }

    public void setAlarmStatusMap(Map<Integer, String> alarmStatusMap) {
        this.alarmStatusMap = alarmStatusMap;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<AlarmHistory> getAlarmArray() {
        return alarmArray;
    }

    public void setAlarmArray(List<AlarmHistory> alarmArray) {
        this.alarmArray = alarmArray;
    }

    public Map<Integer, String> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<Integer, String> dateMap) {
        this.dateMap = dateMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
