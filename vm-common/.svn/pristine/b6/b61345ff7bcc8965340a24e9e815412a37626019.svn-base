package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.DomainSuffix;

/**
 * @author lzc
 *
 */
public interface DomainSuffixProxy {

	/**
	 * 取得所有后缀信息
	 * @return
	 */
	public List<? extends DomainSuffix> findAll() throws Exception;

	/**
	 * 分页取得后缀信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends DomainSuffix> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个域名后缀
	 * @param id 后缀id
	 * @return
	 */
	public DomainSuffix getById(Integer id) throws Exception;
	
	/**
	 * 保存一个域名后缀
	 * @param suffix
	 * @return 新保存suffix在数据库中的id
	 */
	public Integer save(DomainSuffix suffix) throws Exception;
	
	/**
	 * 更新一个域名后缀，仅仅留在这里，但前端不调用该接口
	 * @param suffix
	 * @return
	 */
	public void update(DomainSuffix suffix) throws Exception;
	
	/**
	 * 删除一个域名后缀，仅仅留在这里，但前端不调用该接口
	 * @param suffixId
	 * @return
	 */
	public void deleteById(Integer suffixId) throws Exception;
}
