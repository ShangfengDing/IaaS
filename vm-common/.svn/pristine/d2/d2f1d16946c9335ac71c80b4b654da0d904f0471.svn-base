package appcloud.common.proxy;

import appcloud.common.model.Load;

/**
 * @author lzc
 *
 */
public interface LoadProxy {

	/**
	 * 取得host主机或者instance实例，当前的负载信息
	 * @param uuid
	 * @return
	 */
	public Load getCurLoadByUuid(String uuid) throws Exception;
	
	/**
	 * 保存一条负载信息
	 * @param load
	 * @return
	 */
	public void save(Load load) throws Exception;
	
	/**
	 * 删除某host主机或者instance实例的所有记录
	 * @param uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;
}
