package appcloud.vs.strategy.filter;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vs.Conf;
import appcloud.vs.impl.DBUtil;


/**
 * judge the host by disk!
 *
 */
public class DiskFilter extends BaseHostFilter {
	private static final Logger logger = Logger.getLogger(DiskFilter.class);
	
	@Override
	public boolean hostPass(Host host, Integer size, Host exHost) {
		try {
			//本身硬盘预留量
			Integer freeDisk = Conf.config.getInt(Conf.FREE_DISK, Conf.DEFAULT_FREE_DISK);
			//获取超卖比例
			Integer overSell = DBUtil.getInstance().getClusterById(host.getClusterId()).getDiskOversell();
			//计算此主机可卖出的硬盘大小
			Integer overSellDiskCap = (int) ((host.getDiskMb() * (1.0*overSell/Conf.OVER_SELL_BASE))/1024);
			//获取剩余可买量
			Integer diskUsed = DBUtil.getInstance().getHostUsedDisk(host.getUuid());
			float diskLeft = overSellDiskCap - diskUsed;
			logger.info(host.getIp()+", overSellDiskCap："+ overSellDiskCap +"G, diskUsed:" + diskUsed + "G, diskLeft: "+diskLeft +"G"+", imgSize:"+size+"G, freedisk:"+freeDisk+"G");
			if(diskLeft > size + freeDisk){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
