

package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.J2EEApp;
import appcloud.common.model.J2EEInfo;
import appcloud.common.util.query.QueryObject;

public interface J2EEInfoProxy {

	/**
	 * 取得某个dev的所有大应用
	 * @param devId 开发者id
	 * @return
	 */
	public List<? extends J2EEInfo> getByDevId(Integer devId) throws Exception;
	
	/**
	 * 分页取得某个dev的所有大应用
	 * @param devId 开发者id
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小 0代表不分页
	 * @return
	 */
	public List<? extends J2EEInfo> getByDevId(Integer devId, Integer page, Integer size) throws Exception;
	
	/**
	 * 取得某个开发者的总应用数
	 * @return
	 */
	public long countByDevId(Integer devId) throws Exception;
	
	/**
	 * 取得某个大应用信息
	 * @param id 大应用id
	 * @return
	 */
	public J2EEInfo getById(Integer id) throws Exception;
	
	/**
	 * 保存一个大应用
	 * @param j2eeInfo
	 * @return 新保存的J2eeInfo的id
	 */
	public Integer save(J2EEInfo j2eeInfo) throws Exception;
	
	/**
	 * 更新一个大应用
	 * @param j2eeInfo
	 * @return
	 */
	public void update(J2EEInfo j2eeInfo) throws Exception;
	
	/**
	 * 根据id删除一个大应用
	 * @param j2eeInfoId
	 * @return
	 */
	public void deleteById(Integer j2eeInfoId) throws Exception;
	
	//four functions added by XuanJiaxing
	/**
	 * 取得所有J2EE应用信息
	 * @param withDev 是否同时取得开发者信息
	 * @param withRoutingEntry 是否同时取得域名指向
	 * @return
	 */
	public List<? extends J2EEInfo> findAll(boolean withDev, boolean withRoutingEntry) throws Exception;

	/**
	 * 分页取得J2EE应用信息，根据应用状态进行筛选
	 * @param withDev 是否同时取得开发者信息
	 * @param withRoutingEntry 是否同时取得域名指向
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends J2EEInfo> findAll(boolean withDev, boolean withRoutingEntry, 
			Integer page, Integer size) throws Exception;
	
	/**
	 * 根据查询条件，搜索应用信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withRoutingEntry 是否同时取得域名指向
	 * @return
	 */
	public List<? extends J2EEInfo> searchAll(QueryObject<J2EEInfo> queryObject,
			boolean withDev, boolean withRoutingEntry) throws Exception;

	/**
	 * 根据查询条件，分页搜索应用信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withRoutingEntry 是否同时取得域名指向
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends J2EEInfo> searchAll(QueryObject<J2EEInfo> queryObject,
			boolean withDev, boolean withRoutingEntry, 
			Integer page, Integer size) throws Exception;
	
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<J2EEInfo> queryObject) throws Exception;
	
}
