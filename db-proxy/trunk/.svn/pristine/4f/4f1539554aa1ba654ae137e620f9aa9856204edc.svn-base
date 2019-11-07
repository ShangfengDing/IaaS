package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.VmImage;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.MySQLVmImageProxy;

public class MySQLVmImageProxyTest {

	static MySQLVmImageProxy proxy = null;
	static String testName = "testImageName";
	static String testUuid = "testUuid";
	static final String ReseverdName = "ImageTestReserved1";
	static final String ReseverdUuid = "ImageTestReserved1";
	static final Integer ReseverdId = 1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLVmImageProxy();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void testFindAll() throws Exception {
		List<? extends VmImage> images = proxy.findAll();
		assertTrue("findAll()", images.size()!=0);
	}

	@Test
	public void testFindAllIntegerInteger() throws Exception {
		List<? extends VmImage> images = proxy.findAll(0, 10);
		assertTrue("findAll(page, size)", images.size()!=0);
		assertTrue("findAll(page, size) size <= 10", images.size()<=10);
	}

	@Test
	public void testCountAll() throws Exception {
		long countNum = proxy.countAll();
		assertTrue("countAll()", countNum>0);
	}

	@Test
	public void testSearchAllQueryObjectOfVmImage() throws Exception {
		//search其实不用进行测试
		QueryObject<VmImage> queryObject = new QueryObject<VmImage>();
		queryObject.addFilterBean(new FilterBean<VmImage>("name", ReseverdName, FilterBeanType.EQUAL));
		List<? extends VmImage> images = proxy.searchAll(queryObject);
		assertTrue("searchAll(queryObject)", images.size()!=0);
		assertTrue("searchAll(queryObject) result", images.get(0).getName().equals(ReseverdName));
	}

	@Test
	public void testSearchAllQueryObjectOfVmImageIntegerInteger() throws Exception {
		//search其实不用进行测试
		QueryObject<VmImage> queryObject = new QueryObject<VmImage>();
		queryObject.addFilterBean(new FilterBean<VmImage>("name", ReseverdName, FilterBeanType.EQUAL));
		List<? extends VmImage> images = proxy.searchAll(queryObject, 0, 10);
		assertTrue("searchAll(queryObject, page, size)", images.size()!=0);
		assertTrue("searchAll(queryObject) result", images.get(0).getName().equals(ReseverdName));
	}

	@Test
	public void testCountSearch() throws Exception {
		//search其实不用进行测试
		QueryObject<VmImage> queryObject = new QueryObject<VmImage>();
		queryObject.addFilterBean(new FilterBean<VmImage>("name", ReseverdName, FilterBeanType.EQUAL));
		long countSearchNum = proxy.countSearch(queryObject);
		assertTrue("countSearch(queryObject)", countSearchNum>0);
	}

	@Test
	public void testGetByUuid() throws Exception {
		VmImage image = proxy.getByUuid(ReseverdUuid);
		assertTrue("getByUuid", image.getUuid().equals(ReseverdUuid));
	}

	@Test
	public void testGetById() throws Exception {
		VmImage image = proxy.getById(ReseverdId);
		assertTrue("getById", image!=null);
	}

	@Test
	public void testGetByImageName() throws Exception {
		VmImage image = proxy.getByImageName(ReseverdName);
		assertTrue("getByImageName", image.getName().equals(ReseverdName));
	}

	@Test
	public void testSave() throws Exception {
		VmImage image = new VmImage();
		image.setUuid(testUuid);
		image.setName(testName);
		/*image.setIsPublic(false);*/
		proxy.save(image);
		VmImage savedImage = proxy.getByImageName(testName);
		assertTrue("save", savedImage.getName().equals(testName));
	}

	@Test
	public void testUpdate() throws Exception {
		List<? extends VmImage> images = proxy.findAll();
		for(VmImage image : images){
			if (image.getName().equals(testName)) {
				/*image.setIsPublic(true);*/
				proxy.update(image);
				
				VmImage newImage = proxy.getById(image.getId());
				/*assertTrue("update isPublic=true", newImage.getIsPublic().equals(true));*/
				break;
			}
		}
	}

	@Test
	public void testDeleteById() throws Exception {
		List<? extends VmImage> images = proxy.findAll();
		for(VmImage image : images){
			if (image.getName().equals(testName)) {
				proxy.deleteById(image.getId());
				
				VmImage newImage = proxy.getById(image.getId());
				assertNull("delete", newImage);
				break;
			}
		}
	}
}
