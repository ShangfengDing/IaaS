package appcloud.common.proxy;

import java.util.Calendar;

import appcloud.common.model.NetworkLoad;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 */
public interface NetworkLoadProxy {

	/**
	 * 取得host主机或者instance实例，当前的网络负载信息
	 * @param uuid
	 * @return
	 */
	public NetworkLoad getCurNetLoadByUuid(String uuid) throws Exception;
	
	/**
	 * 获取一个实例在一段时间内的网络负载
	 * @param uuid
	 * @param st
	 * @param ed
	 * @return
	 * @throws Exception
	 */
	public NetworkLoad getNetLoadByUuid(String uuid, Calendar st, Calendar ed) throws Exception;
	
	/**
	 * 获取一个版本在一段时间内的网络负载
	 * @param appUuid
	 * @param st
	 * @param ed
	 * @return
	 * @throws Exception
	 */
	public NetworkLoad getVersionNetLoad(String appUuid, Calendar st, Calendar ed) throws Exception;
	
	/**
	 * 获取一个应用在一段时间内的网络负载
	 * @param j2eeinfoId
	 * @param st
	 * @param ed
	 * @return
	 * @throws Exception
	 */
	public NetworkLoad getAppNetLoad(Integer j2eeinfoId, Calendar st, Calendar ed) throws Exception;
	
	/**
	 * 根据条件查询一个时间段的负载
	 * @param name
	 * @param value
	 * @param st
	 * @param ed
	 * @return
	 * @throws Exception
	 */
	public NetworkLoad getNetLoad(QueryObject<NetworkLoad> queryObject, Calendar st, Calendar ed) throws Exception;
	
	/**
	 * 保存一条网络负载信息
	 * @param networkLoad
	 * @return
	 */
	public void save(NetworkLoad networkLoad) throws Exception;

	/**
	 * 删除某host主机或者instance实例的所有记录
	 * @param uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;
}
