package appcloud.dbproxy.mysql;

import appcloud.common.model.*;
import appcloud.common.proxy.VmImageBackProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.*;
import appcloud.dbproxy.mysql.model.VmImageBackTable;

import java.util.List;

/**
 * Created by lizhenhao on 2017/11/30.
 */
public class MYSQLVmImageBackProxy implements VmImageBackProxy {

    private static VmImageBackDAO dao = new VmImageBackDAO();
    @Override
    public List<? extends VmImageBack> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public List<? extends VmImageBack> findAll(Integer page, Integer size) throws Exception {
        return dao.findAll(page,size);
    }

    @Override
    public long countAll() throws Exception {
        return dao.countAll();
    }

    @Override
    public List<? extends VmImageBack> searchAll(QueryObject<VmImageBack> queryObject) throws Exception {
        return searchAll(queryObject,0,0);
    }

    @Override
    public List<? extends VmImageBack> searchAll(QueryObject<VmImageBack> queryObject, Integer page, Integer size) throws Exception {
        List<? extends VmImageBack> imageBacks = dao.findByProperties(queryObject,page,size);
        return imageBacks;
    }

    @Override
    public long countSearch(QueryObject<VmImageBack> queryObject) throws Exception {
        return dao.countByProperties(queryObject);
    }

    @Override
    public VmImageBack getByUuid(String uuid) throws Exception {
        List<? extends VmImageBack> imageBacks = dao.findByProperty("volumeUuid",uuid);
        VmImageBack vmImageBack = null;
        if (!imageBacks.isEmpty()) {
            vmImageBack = imageBacks.get(0);
        }
        return vmImageBack;
    }

    @Override
    public VmImageBack getByUuid(String uuid, boolean withCluster, boolean withZone, boolean withInstance,boolean withHost) throws Exception {
        List<VmImageBackTable> vmImageBacks = dao.findByProperty("volumeUuid", uuid);

        if (vmImageBacks.size() < 1){
            return null;
        }
        else{
            VmImageBack imageBack = vmImageBacks.get(0);
            fillUpVmImageBack(imageBack, withCluster, withZone,withInstance,withHost);
            return imageBack;
        }
    }


    private void fillUpVmImageBack(VmImageBack imageBack,boolean withCluster, boolean withZone,boolean withInstance, boolean withHost) {
        if(imageBack == null)
            return;
        if(withHost && imageBack.getHostUuid()!=null){
            List<? extends Host> hosts = (new HostDAO()).findByProperty("uuid", (imageBack.getHostUuid()));
            if(!hosts.isEmpty())
                imageBack.setHost(hosts.get(0));
        }
        if(withCluster && imageBack.getAvailabilityClusterId()!=null){
            Cluster cluster = (new ClusterDAO()).findById(imageBack.getAvailabilityClusterId());
            imageBack.setCluster(cluster);
        }
        if(withZone && imageBack.getAvailabilityZoneId()!=null){
            VmZone zone = (new VmZoneDAO()).findById(imageBack.getAvailabilityZoneId());
            imageBack.setZone(zone);
        }
        if(withInstance && imageBack.getInstanceUuid() !=null){
            List<? extends VmInstance> vmInstances = (new VmInstanceDAO()).findByProperty("uuid",imageBack.getInstanceUuid());
            if(!vmInstances.isEmpty())
                imageBack.setVmInstance(vmInstances.get(0));
        }
    }

    @Override
    public VmImageBack getByInstanceUuidAndTop(String instanceUuid, boolean isTop) throws Exception {
        List<VmImageBackTable> list = dao.findByProperty2("instanceUuid",instanceUuid,"top",isTop);
        if (list != null && list.size() > 0) return list.get(0);
        else return null;
    }

    @Override
    public VmImageBack getByVolumeUuidAndTop(String volumeUuid, boolean isTop) throws Exception {
        List<VmImageBackTable> list = dao.findByProperty2("activeVolumeUuid",volumeUuid,"top",isTop);
        if (list != null && list.size() > 0) return list.get(0);
        else return null;
    }

    @Override
    public List<? extends VmImageBack> getByInstanceUuid(String instanceUuid) throws Exception {
        return dao.findByProperty("instanceUuid",instanceUuid);
    }

    @Override
    public VmImageBack getById(Integer imageId) throws Exception {
        return dao.findById(imageId);
    }


    @Override
    public void save(VmImageBack image) throws Exception {
        dao.save(new VmImageBackTable(image));
    }

    @Override
    public void update(VmImageBack image) throws Exception {
        dao.update(new VmImageBackTable(image));
    }

    @Override
    public void deleteById(Integer imageId) throws Exception {
        dao.deleteByPrimaryKey(imageId);
    }
}
