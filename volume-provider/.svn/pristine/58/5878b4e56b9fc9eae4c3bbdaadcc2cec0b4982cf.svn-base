package appcloud.vp.libvirt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.StoragePool;
import org.libvirt.StorageVol;
import org.libvirt.StorageVolInfo;

import appcloud.vp.Conf;

/**
 * only for test.
 * @author wangchao
 *
 */
public class VmManager {
	 private VmManager() {}

	    @Override
	    protected void finalize() throws Throwable {
	        super.finalize();
	        if (virtCon != null) {
	            virtCon.close();
	        }
	    }

	    private static Connect virtCon = null;
	    private static VmManager instance = null;

	    private static Logger logger = Logger.getLogger(VmManager.class);


	    /**
	     * 获取实例
	     * @return
	     * @throws LibvirtException 
	     * @throws Exception
	     */
	    public static VmManager getInstance() throws LibvirtException {
	        if (instance == null)
	        {
	            logger.info("initializing...");
	            instance = new VmManager();
	            virtCon = new Connect(Conf.LIBVIRT_CONN);
	            // 用于 观察
	            if (Conf.DEBUG) {
	                List<String> list = instance.getDomainUuidList();
	                for (String uuid : list) {
	                    logger.debug(virtCon.domainLookupByUUIDString(uuid).getXMLDesc(0));
	                }
	            }
	            logger.info("initialized.");
	        }

	        return instance;
	    }
	    
	    public List<String> getDomainUuidList() {
	        List<String> list = new ArrayList<String>();
	        try {
	        	logger.info("domain num:" + virtCon.listDomains().length);
	        	logger.info("defined domain num:" + virtCon.listDefinedDomains().length);
	        	logger.info("num:" + virtCon.numOfDomains());
	            for (String name : virtCon.listDefinedDomains()) {
	            	logger.info("lookup name:　" + name);
	            	if (name != null) {
		                Domain d = virtCon.domainLookupByName(name);                
		                list.add(d.getUUIDString().replace("-", ""));
	            	}
	            }
	            for (int id : virtCon.listDomains()) {
	            	logger.info("lookup id:　" + id);
	                Domain d = virtCon.domainLookupByID(id);
	                list.add(d.getUUIDString().replace("-", ""));
	            }
	        } catch (Exception ex) {
	        	logger.warn(ex);
	        }
	        return list;
	    }
	    
	    void getStoragePoolsList() {
	    	try {
				String[] list = virtCon.listStoragePools();
				for (String sp : list) {
					logger.info(sp);
				}
				StoragePool p = virtCon.storagePoolLookupByName("image");
				list = p.listVolumes();
				for (String sp : list) {
					logger.info(sp);
				}
				StorageVol v = p.storageVolLookupByName("rhel_test.img");
				//virtCon.storageVolLookupByKey("rhel_test.img");
				StorageVolInfo info = v.getInfo();
				logger.info(info.toString());
			} catch (LibvirtException e) {
				e.printStackTrace();
			}
	    	
	    }
}
