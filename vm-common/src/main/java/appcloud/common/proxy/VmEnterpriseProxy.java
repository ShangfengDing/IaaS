package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmEnterprise;
import appcloud.common.util.query.QueryObject;

/**
 * @author LinXiong
 *
 */
public interface VmEnterpriseProxy {
	/**
	 * 保存一个企业信息
	 * @param enterprise
	 * @return
	 */
	public void save(VmEnterprise enterprise) throws Exception;
	
	/**
	 * 更新一个企业信息
	 * @param enterprise
	 * @return
	 */
	public void update(VmEnterprise enterprise) throws Exception;
	
	/**
	 * 删除一个企业信息
	 * @param enterpriseId
	 * @return
	 */
	public void deleteById(Integer enterpriseId) throws Exception;
	
	/**
	 * 取得所有企业信息
	 * @return
	 */
	public List<? extends VmEnterprise> findAll() throws Exception;
	
	/**
	 * 分页取得企业信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmEnterprise> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索企业信息
	 * @param queryObject 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmEnterprise> searchAll(QueryObject<VmEnterprise> queryObject) throws Exception;

	/**
	 * 根据查询条件，分页搜索企业信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmEnterprise> searchAll(QueryObject<VmEnterprise> queryObject,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmEnterprise> queryObject) throws Exception;
	
	
	/**
	 * 根据id查询企业
	 * @param id
	 * @return
	 */
	public VmEnterprise getById(Integer id) throws Exception;
	
	/**
	 * 根据所有者查询企业
	 * @param ownerId
	 * @return
	 */
	public VmEnterprise getByOwnerId(Integer ownerId) throws Exception;
	
	/**
	 * 根据企业状态查询企业
	 * @param status
	 * @return
	 */
	public List<? extends VmEnterprise> getByStatus(Boolean status, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据名字查询企业
	 * @param name
	 * @return
	 */
	public List<? extends VmEnterprise> getByName(String name, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据企业电话查询
	 * @param phone
	 * @return
	 */
	public List<? extends VmEnterprise> getByPhone(String phone, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据企业email查询
	 * @param email
	 * @return
	 */
	public List<? extends VmEnterprise> getByEmail(String email, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据企业地址查询
	 * @param address
	 * @return
	 */
	public List<? extends VmEnterprise> getByAddress(String address, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据企业邮编查询
	 * @param postcode
	 * @return
	 */
	public List<? extends VmEnterprise> getByPostcode(String postcode, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据企业主页查询
	 * @param homepage
	 * @return
	 */
	public List<? extends VmEnterprise> getByHomepage(String homepage, Integer page, Integer size) throws Exception;
	
	/**
	 * 根据父公司查询
	 */
	public List<? extends VmEnterprise> getByParentCompanyId(Integer parentCompanyId, Integer page, Integer size) throws Exception;
}
