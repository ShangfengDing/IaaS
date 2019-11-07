package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmInstanceType;
import appcloud.common.util.query.QueryObject;
/**
 * @author XuanJiaxing
 *
 */
public interface VmInstanceTypeProxy {
	/**
	 * 取得所有套餐信息信息
	 * @return
	 */
	public List<? extends VmInstanceType> findAll() throws Exception;
	
	/**
	 * 分页取得套餐信息信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmInstanceType> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索套餐信息信息
	 * @param properties 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmInstanceType> searchAll(QueryObject<VmInstanceType> queryObject) throws Exception;

	/**
	 * 根据查询条件，分页搜索套餐信息信息
	 * @param properties 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmInstanceType> searchAll(QueryObject<VmInstanceType> queryObject,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmInstanceType> queryObject) throws Exception;
	
	/**
	 * 根据uuid取得某个套餐信息信息
	 * @param flavorUuid
	 * @return
	 */
	public VmInstanceType getByUuid(String flavorUuid) throws Exception;
	
	
	/**
	 * 取得某个套餐信息信息
	 * @param instanceTypeId
	 * @return
	 */
	public VmInstanceType getById(Integer instanceTypeId) throws Exception;
	
	/**
	 * 根据套餐信息名称，取得某个instanceType信息
	 * @param name
	 * @return
	 */
	public VmInstanceType getByName(String name) throws Exception;
	
	/**
	 * 保存一个套餐信息信息
	 * @param instanceType
	 * @return
	 */
	public void save(VmInstanceType instanceType) throws Exception;
	
	/**
	 * 更新一个套餐信息信息
	 * @param instanceType
	 * @return
	 */
	public void update(VmInstanceType instanceType) throws Exception;
	
	/**
	 * 根据id删除一个套餐信息信息
	 * @param instanceTypeId 
	 * @return
	 */
	public void deleteById(Integer instanceTypeId) throws Exception;
	
	/**
     * 根据uuid删除一个套餐信息信息
     * @param instanceTypeId 
     * @return
     */
    public void deleteByUuid(String uuid) throws Exception;
}
