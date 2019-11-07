package appcloud.dbproxy.mysql;

import appcloud.common.model.VmImageBack;
import appcloud.dbproxy.mysql.dao.VmImageBackDAO;

public class TestVmInstanceMetaProxy {
    public static void main(String[] args) {
//    	VmInstanceMetadataDao __vmInstanceMetadataDao = new VmInstanceMetadataDao();
//
//        try {
//			int len = __vmInstanceMetadataDao.findAll().size();
//			System.out.println("size is "+len);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		VmImageBackDAO dao = new VmImageBackDAO();
		VmImageBack back = dao.findByProperty2("instanceUuid","41165684d91245afaa6dc172bec89353","top",true).get(0);
		if (back != null) System.out.println(back.isTop());
	}
}
