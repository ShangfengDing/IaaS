package appcloud.dbproxy.mysql;


import appcloud.common.model.VmSummary;
import appcloud.common.proxy.VmSummaryProxy;
import appcloud.dbproxy.mysql.dao.AlertMsgDAO;
import appcloud.dbproxy.mysql.dao.VmSummaryDAO;
import appcloud.dbproxy.mysql.model.VmSummaryTable;
import com.kenai.jaffl.annotations.In;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MySQLVmSummaryProxy implements VmSummaryProxy {

    private final static Logger logger = Logger.getLogger(MySQLVmSummaryProxy.class);
    private static VmSummaryDAO dao = new VmSummaryDAO();

    @Override
    public List<? extends VmSummary> findAll() throws Exception{
        List<? extends VmSummary> vmSummaries = dao.findAll();
        return vmSummaries;
    }

    @Override
    public List<? extends VmSummary> findAll(Integer page, Integer size) throws Exception {
        return dao.findAll(page, size);
    }

    @Override
    public VmSummary findByUuid(String uuid) throws Exception {
        VmSummary vmSummary = dao.findByProperty("uuid",uuid).get(0);
        return vmSummary;
    }

    @Override
    public void save(VmSummary vmSummary) throws Exception{
        dao.save(new VmSummaryTable(vmSummary));
    }

    @Override
    public void deleteByUuid(String uuid) throws Exception{
        dao.deleteByProperty("uuid",uuid);
    }

    @Override
    public void deleteByName(String name) throws Exception{
        dao.deleteByPrimaryKey(name);
    }

    @Override
    public void update(VmSummary vmSummary) throws Exception{
        dao.update(new VmSummaryTable(vmSummary));
    }

    @Override
    public long countAll(){return dao.countAll();}

    @Override
    public void deleteById(Integer id) throws Exception{
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public VmSummary findById(Integer id) throws Exception{
        VmSummary vmSummary = dao.findByPrimaryKey(id);
        return vmSummary;
    }

//    @Override
//    public List<VmSummary> findByType(String type) throws Exception{
//        List<VmSummaryTable> vmSummaryTableList = dao.findByProperty("name",type);
//        List<VmSummary> vmSummary = new ArrayList<VmSummary>();
//        for(int i=0; i<vmSummary.size();i++){
//            vmSummary.add(vmSummaryTableList.get(i));
//        }
//        return vmSummary;
//    }

    @Override
    public List<? extends VmSummary> findByType(String type) throws Exception{
        List<? extends VmSummary> vmSummary = new ArrayList<VmSummary>();
        if (type.equals("NETDEVICE")){
            vmSummary = dao.findByProperty("type",VmSummary.VmSummaryTypeEnum.NETDEVICE);
        } else if (type.equals("DISKDEVICE")){
            vmSummary = dao.findByProperty("type",VmSummary.VmSummaryTypeEnum.DISKDEVICE);
        } else if (type.equals("OTHER")){
            vmSummary = dao.findByProperty("type",VmSummary.VmSummaryTypeEnum.OTHER);
        } else{
            logger.error("find by type"+type+"error");
        }
        return vmSummary;
    }
}
