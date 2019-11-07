package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.DomainSuffix;
import appcloud.common.proxy.DomainSuffixProxy;
import appcloud.dbproxy.mysql.dao.DomainSuffixDAO;
import appcloud.dbproxy.mysql.model.DomainSuffixTable;

public class MySQLDomainSuffixProxy implements DomainSuffixProxy{
	private static DomainSuffixDAO dao = new DomainSuffixDAO();

	@Override
	public List<? extends DomainSuffix> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends DomainSuffix> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public DomainSuffix getById(Integer id) throws Exception {
		return dao.findByPrimaryKey(id);
	}

	@Override
	public Integer save(DomainSuffix suffix) throws Exception {
		List<? extends DomainSuffix> suffixs = dao.findByProperty("name", suffix.getName());
		if (!suffixs.isEmpty()) {
			throw new Exception("后缀已存在，不能再保存");
		}
		DomainSuffixTable table = new DomainSuffixTable(suffix);
		dao.save(table);
		return table.getId();
	}

	@Override
	public void update(DomainSuffix suffix) throws Exception {
		dao.update(new DomainSuffixTable(suffix));
	}

	@Override
	public void deleteById(Integer suffixId) throws Exception {
		dao.deleteByPrimaryKey(suffixId);
	} 

}