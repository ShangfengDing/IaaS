package appcloud.vs.strategy;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import sun.util.logging.resources.logging;

import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.common.util.ConnectionFactory;
import appcloud.vs.Conf;
import appcloud.vs.impl.DBUtil;
/**
 * @author wangchao
 *
 */
public class FreediskHostSorter implements IHostSorter{
	private static Logger logger = Logger.getLogger(FreediskHostSorter.class);
	
	public int scoreHost(Host host) throws Exception {
//		float diskLeft = host.getDiskMb() - DBUtil.getInstance().getHostUsedDisk(host.getUuid());
		float diskLeft = host.getDiskMb() - (DBUtil.getInstance().getFreshHostLoad(host.getUuid()).getDiskPercent() * host.getDiskMb()/100);
		String params = DBUtil.getInstance().getDiskResourceStrategy(host.getClusterId()).getParams();
		JSONObject json = new JSONObject(params);
        Integer weight = (Integer) json.get(Conf.DISK);
        
		return (int) diskLeft*weight;
	}

	public void setHosts(List<Host> hosts) {
		// this sorter is very simple and does not care about other hosts
	}

}
