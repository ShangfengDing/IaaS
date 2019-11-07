package appcloud.admin.action.system;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.util.ConnectionFactory;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VmHostAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(VmSummaryAction.class);
    private List<Host> hostList=new ArrayList<>();
    private HostProxy hostProxy= ConnectionFactory.getHostProxy();
    private ClusterProxy clusterProxy=ConnectionFactory.getClusterProxy();
    private Map<String,String> clusterMap=new HashMap<>();
    private long count;

    private String uuid;
    private String result;
    private Host host;
    private String name;
    private String extend;
    private String type;

    public String findAllNode(){
        try {
            count = hostProxy.countAll();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("count all node error");
        }
        try {
           hostList = (List<Host>)hostProxy.findAll(false, false, false);
            List<Cluster> clusterList=(List<Cluster>)clusterProxy.findAll(false,false,false);
            for(Cluster c:clusterList) {
                clusterMap.put(c.getId()+"",c.getName());
            }
            orderList();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("find All node error");
        }
       // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+hostList.size());
        return Action.SUCCESS;
    }

    private void orderList(){
        for(int i=0;i<hostList.size();i++){
            for(int j=0;j<hostList.size()-i-1;j++){
                if(hostList.get(j).getId()<hostList.get(j+1).getId()){
                    Host host=hostList.get(j);
                    hostList.set(j,hostList.get(j+1));
                    hostList.set(j+1,host);
                }
            }
        }

    }

    public String findNodeByUUID(){
        try{
            host=hostProxy.getByUuid(uuid,false,false,false);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("find by UUID error");
            result="error";
            return Action.ERROR;
        }
        result="success";
        return Action.SUCCESS;
    }

    public String modifyNode(){
        try{
            host=hostProxy.getByUuid(uuid,false,false,false);
            host.setName(name);
            host.setExtend(extend);
            hostProxy.update(host);
        }catch(Exception e){
            e.printStackTrace();
            result="error";
            return Action.ERROR;
        }
        result="success";
        return Action.SUCCESS;
    }

    public String findNodeByType(){
        try{
            hostList=(List<Host>)hostProxy.findByType(type);
            count=hostList.size();
            List<Cluster> clusterList=(List<Cluster>)clusterProxy.findAll(false,false,false);
            for(Cluster c:clusterList) {
                clusterMap.put(c.getId()+"",c.getName());
            }
        }catch(Exception e){
            logger.error("find HOST by type error");
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public Map<String, String> getClusterMap() {
        return clusterMap;
    }

    public void setClusterMap(Map<String, String> clusterMap) {
        this.clusterMap = clusterMap;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
