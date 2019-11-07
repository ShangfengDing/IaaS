package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmEnterpriseInvitation;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;
import appcloud.common.model.VmUser;
import appcloud.common.util.query.QueryObject;

/**
 * @author LinXiong
 *
 */
public interface VmEnterpriseInvitationProxy {
	/**
	 * 保存一个企业邀请信息
	 * @param vmEnterpriseInvitation
	 * @return
	 */
	public void save(VmEnterpriseInvitation vmEnterpriseInvitation) throws Exception;
	
	/**
	 * 更新一个企业邀请信息
	 * @param vmEnterpriseInvitation
	 * @return
	 */
	public void update(VmEnterpriseInvitation vmEnterpriseInvitation) throws Exception;
	
	/**
	 * 删除一个用户邀请信息
	 * @param vmEnterpriseInvitationId
	 * @return
	 */
	public void deleteById(Integer vmEnterpriseInvitationId) throws Exception;
	
	/**
	 * 取得所有企业邀请信息
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> findAll() throws Exception;
	
	/**
	 * 分页取得所有企业邀请信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 根据查询条件，搜索企业信息
	 * @param queryObject 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> searchAll(QueryObject<VmEnterpriseInvitation> queryObject) throws Exception;

	/**
	 * 根据查询条件，分页搜索企业信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> searchAll(QueryObject<VmEnterpriseInvitation> queryObject,
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
	public long countSearch(QueryObject<VmEnterpriseInvitation> queryObject) throws Exception;
	
	/**
	 * 按id查询企业邀请信息
	 * @param id
	 * @return 
	 */
	public VmEnterpriseInvitation getById(Integer id) throws Exception;
	
	/**
	 * 按企业id查询邀请信息
	 * @param enterpriseId
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> getByEnterpriseId(Integer enterpriseId, Integer page, Integer size) throws Exception;
	
	/**
	 * 按用户id查询邀请信息
	 * @param userId
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> getByUserId(Integer userId, Integer page, Integer size) throws Exception;
	
	/**
	 * 按状态查询邀请信
	 * @param status
	 * @return
	 */
	public List<? extends VmEnterpriseInvitation> getByStatus(VmEnterpriseInvitationStatus status, Integer page, Integer size) throws Exception;
	
}
