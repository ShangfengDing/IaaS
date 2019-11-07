package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmImage;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmImageDAO;
import appcloud.dbproxy.mysql.model.VmImageTable;

/**
 * @author XuanJiaxing
 * 
 */
public class MySQLVmImageProxy implements VmImageProxy{

	private static VmImageDAO dao = new VmImageDAO();
	@Override
	public List<? extends VmImage> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmImage> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmImage> searchAll(
			QueryObject<VmImage> queryObject) throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends VmImage> searchAll(
			QueryObject<VmImage> queryObject, Integer page, Integer size)
			throws Exception {
		List<? extends VmImage> images = dao.findByProperties(queryObject, page, size);
		return images;
	}

	@Override
	public long countSearch(QueryObject<VmImage> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmImage getByUuid(String uuid) throws Exception {
		List<? extends VmImage> images = dao.findByProperty("uuid", uuid);
		VmImage image = null;
		if (!images.isEmpty()) {
			image = images.get(0);
		}
		return image;
	}

	@Override
	public VmImage getById(Integer imageId) throws Exception {
		return dao.findById(imageId);
	}

	@Override
	public VmImage getByImageName(String name) throws Exception {
		List<? extends VmImage> images = dao.findByProperty("name", name);
		VmImage image = null;
		if (!images.isEmpty()) {
			image = images.get(0);
		}
		return image;
	}

	@Override
	public void save(VmImage image) throws Exception {
		dao.save(new VmImageTable(image));
	}

	@Override
	public void update(VmImage image) throws Exception {
		dao.update(new VmImageTable(image));
	}

	@Override
	public void deleteById(Integer imageId) throws Exception {
		dao.deleteByPrimaryKey(imageId);
	}

	@Override
	public List<? extends VmImage> getByMd5(String md5) throws Exception {
		List<? extends VmImage> images = dao.findByProperty("md5sum", md5);
		return images;
	}

	@Override
	public VmImage getByUuidAndGroupId(String uuid, Integer groupId)
			throws Exception {
		return dao.findByProperty2("uuid", uuid, "groupId", groupId).get(0);
	}

	@Override
	public VmImage getByMd5AndGroupId(String md5, Integer groupId)
			throws Exception {
		return dao.findByProperty2("md5sum", md5, "groupId", groupId).get(0);
	}

}
