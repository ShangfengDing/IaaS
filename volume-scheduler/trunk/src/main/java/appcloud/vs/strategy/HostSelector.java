package appcloud.vs.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.vs.impl.DBUtil;

public class HostSelector implements SelectorService {
	private static Logger logger = Logger.getLogger(HostSelector.class);
	private static DBUtil dbUtil = DBUtil.getInstance();
	
	private static final String[] filterNameList = {"DiskFilter"};

	public List<Host> selectNodes(Cluster cluster, int size, Host exHost){
		logger.info("cluster:"+cluster+", size:"+size+", exHost:"+exHost);
		if (cluster == null) {
			logger.error("cluster is null");
			return Collections.emptyList();
		}
		List<Service> services = dbUtil.getRunningServiceList(cluster.getAvailabilityZoneId(), cluster.getId(), ServiceTypeEnum.VOLUME_PROVIDER);
		List<Host> hosts = new ArrayList<Host>();
		for (Service service : services) {
			hosts.add(service.getHost());
		}
		HostFilterHandler handler = new HostFilterHandler(filterNameList);
		List<Host> selectedHosts = new ArrayList<Host>();
		selectedHosts = handler.getAvailHost(cluster, size, exHost, ServiceTypeEnum.VOLUME_PROVIDER, hosts);
		FreediskHostSorter sorter = new FreediskHostSorter();
		if (selectedHosts == null || selectedHosts.size() == 0) {
			logger.info("selectedHosts's size is null");
			return Collections.emptyList();
		}
		logger.info("before sort hosts:");
		for (Host host : selectedHosts) {
			try {
				logger.info(host.getIp() + "'s freeDsik:"
						+ sorter.scoreHost(host));
			} catch (Exception e) {
				logger.error("freeDiskSorter error");
				e.printStackTrace();
			}
		}
		Collections.sort(selectedHosts, new HostComparator(new FreediskHostSorter(), selectedHosts));
		logger.info("after sort hosts:");
		for (Host host : selectedHosts) {
			try {
				logger.info(host.getIp() + "'s freeDsik:"
						+ sorter.scoreHost(host));
			} catch (Exception e) {
				logger.error("freeDiskSorter error");
				e.printStackTrace();
			}
		}
		List<Host> sameHost = new ArrayList<Host>();
		if (exHost != null) {
			sameHost = handler.getSameHost(cluster, size, exHost, ServiceTypeEnum.VOLUME_PROVIDER, selectedHosts);
		}
		logger.info("same host or not(0 is null):"+sameHost.size());
		if (sameHost != null && sameHost.size() != 0) {
			return sameHost;
		} else {
			return selectedHosts;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String ip = "192.168.1.12";
		QueryObject<Host> queryObject = new QueryObject<Host>();
		queryObject.addFilterBean(new FilterBean<Host>("ip", ip, FilterBeanType.EQUAL));
		Host host = (Host) ((HostProxy)ConnectionFactory.getDBProxy(HostProxy.class)).searchAll(queryObject, false, false, false).get(0);
		logger.info(host);
		List<Host> result = new HostSelector().selectNodes( 
				((ClusterProxy)ConnectionFactory.getDBProxy(ClusterProxy.class)).getById(host.getClusterId(), false, false, false), 
				5, 
				host);
		System.out.println("--------------------------result---------------------------------");
//		logger.info(ip);
		for (Host temp : result) {
			logger.info(temp.getIp());
		}
	}
}
