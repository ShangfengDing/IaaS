package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmImage;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 *
 */
public interface VmImageProxy {
	/**
	 * 取得所有公用镜像信息
	 * @return
	 */
	public List<? extends VmImage> findAll() throws Exception;
	
	/**
	 * 分页取得公用镜像信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmImage> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索公用镜像信息
	 * @param properties 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmImage> searchAll(QueryObject<VmImage> queryObject) throws Exception;

	/**
	 * 根据查询条件，分页搜索公用镜像信息
	 * @param properties 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmImage> searchAll(QueryObject<VmImage> queryObject,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmImage> queryObject) throws Exception;
	
	/**
	 * 根据uuid取得某个公用镜像信息
	 * @param uuid
	 * @return
	 */
	public VmImage getByUuid(String uuid) throws Exception;
	
	public VmImage getByUuidAndGroupId(String uuid, Integer groupId) throws Exception;
	public VmImage getByMd5AndGroupId(String md5, Integer groupId) throws Exception;
	/**
	 * 取得某个公用镜像信息
	 * @param imageId
	 * @return
	 */
	public VmImage getById(Integer imageId) throws Exception;
	
	/**
	 * 根据公用镜像名称，取得某个公用镜像信息
	 * @param name
	 * @return
	 */
	public VmImage getByImageName(String name) throws Exception;
	
	/**
	 * 保存一个公用镜像信息
	 * @param image
	 * @return
	 */
	public void save(VmImage image) throws Exception;
	
	/**
	 * 更新一个公用镜像信息
	 * @param image
	 * @return
	 */
	public void update(VmImage image) throws Exception;
	
	/**
	 * 删除一个公用镜像信息
	 * @param imageId 
	 * @return
	 */
	public void deleteById(Integer imageId) throws Exception;
	
	/**
	 * 返回具有相同md5的条目
	 * @param md5
	 * @return
	 * @throws Exception
	 */
	public List<? extends VmImage> getByMd5(String md5) throws Exception;
}

