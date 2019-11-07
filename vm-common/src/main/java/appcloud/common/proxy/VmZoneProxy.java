package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmZone;

public interface VmZoneProxy {

	/**
	 * 取得所有数据中心信息
	 * @return
	 */
	public List<? extends VmZone> findAll() throws Exception;
	
	/**
	 * 分页取得数据中心信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmZone> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个数据中心信息
	 * @param zoneId 数据中心id
	 * @return
	 */
	public VmZone getById(Integer zoneId) throws Exception;
	
	/**
	 * 根据用户名，取得某个数据中心信息
	 * @param zoneName 数据中心用户名
	 * @return
	 */
	public VmZone getByName(String zoneName) throws Exception;

	/**
	 * 根据云平台所在地域，取得数据中心信息
	 * @param regionId 云平台所在地域 
	 * @return
	 */
	public VmZone getByRegionId(String regionId) throws Exception;

	/**
	 * 根据云平台所在可用区，取得数据中心信息
	 * @param zoneId 云平台所在可用区 
	 * @return
	 */
	public VmZone getByZoneId(String zoneId) throws Exception;
	/**
	 * 保存一个新数据中心信息
	 * @param zone
	 * @return
	 */
	public void save(VmZone zone) throws Exception;
	
	/**
	 * 更新一个数据中心信息
	 * @param zone
	 * @return
	 */
	public void update(VmZone zone) throws Exception;
	
	/**
	 * 删除一个数据中心信息
	 * @param zoneId 数据中心id
	 * @return
	 */
	public void deleteById(Integer zoneId) throws Exception;

}
