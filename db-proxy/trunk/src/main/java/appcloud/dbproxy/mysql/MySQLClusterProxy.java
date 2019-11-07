package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.List;
import appcloud.common.model.Cluster;
import appcloud.common.model.ClusterStatistic;
import appcloud.common.model.Host;
import appcloud.common.model.Instance;
import appcloud.common.model.NetworkLoad;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.ClusterDAO;
import appcloud.dbproxy.mysql.dao.HostDAO;
import appcloud.dbproxy.mysql.dao.NetworkLoadDAO;
import appcloud.dbproxy.mysql.model.ClusterTable;

public class MySQLClusterProxy implements ClusterProxy{
	private static ClusterDAO dao = new ClusterDAO();
	private static HostDAO hostDAO = new HostDAO();

	@Override
	public List<? extends Cluster> findAll(boolean withResrcTaskStrategy,
			boolean withHosts, boolean withNum) {
		List<? extends Cluster> clusters = dao.findAll();
		for (Cluster cluster : clusters) {
			fillUpCluster(cluster, withResrcTaskStrategy, withHosts, withNum);
		}
		return clusters;
	}

	@Override
	public long countAll() {
		try {
			return dao.countAll();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public List<? extends Cluster> findAll(
			boolean withResrcTaskStrategy, boolean withHosts, boolean withNum,
			Integer page, Integer size) {
		List<? extends Cluster> clusters = dao.findAll(page, size);
		for (Cluster cluster : clusters) {
			fillUpCluster(cluster, withResrcTaskStrategy, withHosts, withNum);
		}
		return clusters;
	}

	@Override
	public Cluster getById(Integer id, boolean withResrcTaskStrategy,
			boolean withHosts, boolean withNum) {
		Cluster cluster = dao.findById(id);
		if (cluster != null) {
			fillUpCluster(cluster, withResrcTaskStrategy, withHosts, withNum);
		}
		return cluster;
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ClusterProxy#getByName(java.lang.Integer)
	 */
	@Override
	public Cluster getByName(String name) throws Exception {
		List<? extends Cluster> clusters = dao.findByProperty("name", name);
		if (clusters.size() < 1){
			return null;
		}
		else{
			return clusters.get(0);
		}
	}

	@Override
	public void save(Cluster cluster) {
		dao.save(new ClusterTable(cluster));
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClusterStatistic getClusterStatisticById(Integer clusterId) {
		// TODO Auto-generated method stub
		ClusterStatistic result = new ClusterStatistic();
		
		List<? extends Host> hosts = hostDAO.findByProperty("clusterId", clusterId);
		List<String> hostsUuid = new ArrayList<String>(); 
		
		NetworkLoad networkLoad = new NetworkLoad();
		networkLoad.setNetInByte(0);
		networkLoad.setNetOutByte(0);
		networkLoad.setRequestNum(0);
		
		//集群中主机数量为0时，直接返回
		if (hosts.isEmpty()) {
			result.setNetworkLoad(networkLoad);
			result.setDevNum(0);
			result.setJ2eeAppNum(0);
			result.setJ2eeInstanceNum(0);
			result.setVmNum(0);
			return result;
		}
				
		//计算networkLoad
		Integer netInByte = 0, netOutByte = 0, requestNum = 0;
		NetworkLoadDAO networkLoadDAO = new NetworkLoadDAO();
		for(int i = 0; i < hosts.size(); i++){
			String uuid = hosts.get(i).getUuid();
			if(!uuid.isEmpty()){
				hostsUuid.add(uuid);
				NetworkLoad load = networkLoadDAO.getCurNetLoadByUuid(uuid);
				if(load != null){
					netInByte += load.getNetInByte();
					netOutByte += load.getNetOutByte();
					requestNum += load.getRequestNum();
				}
			}
		}
		
		networkLoad.setNetInByte(netInByte);
		networkLoad.setNetOutByte(netOutByte);
		networkLoad.setRequestNum(requestNum);
		result.setNetworkLoad(networkLoad);
		
		//instanceProxy 供调用
		MySQLInstanceProxy instanceProxy = new MySQLInstanceProxy();
		
		//计算j2ee实例数
		QueryObject<Instance> j2eeQueryInstance = new QueryObject<Instance>();
		j2eeQueryInstance.addFilterBean(new FilterBean<Instance>("hostUuid", hostsUuid, FilterBeanType.IN));
		j2eeQueryInstance.addFilterBean(new FilterBean<Instance>("type", Instance.InstanceTypeEnum.J2EE, FilterBeanType.EQUAL));
		List<Instance> j2eeInstance = new ArrayList<Instance>();
		try {
			j2eeInstance = (List<Instance>)instanceProxy.searchAll(j2eeQueryInstance, true, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setJ2eeInstanceNum(j2eeInstance.size());
		
		//计算j2ee应用数以及开发者数量
		List<String> appUuids = new ArrayList<String>();
		List<Integer> devIds = new ArrayList<Integer>();
		for(int i = 0; i < j2eeInstance.size(); i++){
			Instance instanceTemp = new Instance();
			instanceTemp = j2eeInstance.get(i);
			if(!appUuids.contains(instanceTemp.getAppUuid())){
			    appUuids.add(instanceTemp.getAppUuid());
			}
			try {
			    if( !devIds.contains(instanceTemp.getJ2eeApp().getDevId()) ){
	                devIds.add(instanceTemp.getJ2eeApp().getDevId());
	            }
            }
            catch (Exception e) {
                System.out.println("instanceTemp.getJ2eeApp() error!" + instanceTemp.getUuid());
                e.printStackTrace();
            }
			
		}
		result.setJ2eeAppNum(appUuids.size());
		
		//计算vm实例数
		QueryObject<Instance> vmQueryInstance = new QueryObject<Instance>();
		vmQueryInstance.addFilterBean(new FilterBean<Instance>("hostUuid", hostsUuid, FilterBeanType.IN));
		vmQueryInstance.addFilterBean(new FilterBean<Instance>("type", Instance.InstanceTypeEnum.VM, FilterBeanType.EQUAL));
		List<Instance> vmInstance = new ArrayList<Instance>();
		try {
			vmInstance = (List<Instance>)instanceProxy.searchAll(vmQueryInstance, true, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < vmInstance.size(); i++){
			Instance instanceTemp = new Instance();
			instanceTemp = vmInstance.get(i);
			try {
				if( !devIds.contains(instanceTemp.getVmApp().getDevId()) ){
					devIds.add(instanceTemp.getVmApp().getDevId());
				}
			} catch (NullPointerException e) {//捕获由于instance和app表数据产生不一致时，会出现空指针的异常
			    System.out.println("instanceTemp.getVmApp() error!" + instanceTemp.getUuid());
			    e.printStackTrace();
			}
			
		}
		result.setDevNum(devIds.size());
		result.setVmNum(vmInstance.size());
		return result;
	}

	@Override
	public void update(Cluster cluster) {
		dao.update(new ClusterTable(cluster));
	}

	@Override
	public void deleteById(Integer clusterId) {
		dao.deleteByPrimaryKey(clusterId);
	}

	@SuppressWarnings("unchecked")
	private void fillUpCluster(Cluster cluster, boolean withResrcTaskStrategy, 
			boolean withHosts, boolean withNum) {
		if (withResrcTaskStrategy) {
			try {
				cluster.setResrcStrategys((List<ResourceStrategy>) new MySQLResourceStrategyProxy().getByUuid(cluster.getResrcStrategyUuid()));
				cluster.setTaskStrategy(new MySQLTaskStrategyProxy().getById(cluster.getTaskStrategyId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (withHosts || withNum) {
			QueryObject<Host> hostQuery = new QueryObject<Host>();
			hostQuery.addFilterBean(new FilterBean<Host>("clusterId", cluster.getId(), FilterBeanType.EQUAL));
			List<? extends Host> hosts = hostDAO.findByProperty("clusterId", cluster.getId());
			if (withHosts) {
				cluster.setHosts((List<Host>) hosts);
			}
			if (withNum) {
				cluster.setHostNum(hosts.size());
			}
		}
	}

	@Override
	public List<? extends Cluster> searchAll(QueryObject<Cluster> queryObject)
			throws Exception {
		return dao.findByProperties(queryObject, 0, 0);
	}
}
