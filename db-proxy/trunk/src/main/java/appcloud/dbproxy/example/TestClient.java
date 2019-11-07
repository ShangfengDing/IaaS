package appcloud.dbproxy.example;

import java.util.List;

import appcloud.common.model.Admin;
import appcloud.common.model.Developer;
import appcloud.common.model.Host;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.Admin.AdminRoleEnum;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.DeveloperProxy;
import appcloud.common.proxy.J2EEAppProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tip.TipRPCClient;

public class TestClient {
	
	private static void fillin() {
		/*Developer dev = new Developer();
		dev.setEmail("ley@imley.net");
		dev.setId(123);
		devProxy.saveDev(dev);
		
		J2EEApp app = new J2EEApp();
		app.setUuid("uuid1");
		app.setName("test");
		app.setDevId(123);
		app.setUrl("ffff");
		appProxy.saveJ2EEApp(app);
		
		app = new J2EEApp();
		app.setUuid("uuid2");
		app.setName("minemine");
		app.setDevId(123);
		app.setUrl("1231232");
		appProxy.saveJ2EEApp(app);*/
	}
	
	private static void testJPA() throws Exception {
//		Admin admin = new Admin(null, "1", "2", AdminRoleEnum.APPADMIN, "4", "5", "6");
//		System.out.println("amdin: " + admin);
//		//dao.saveAdmin(admin);
//		AdminProxy proxy = ConnectionFactory.getAdminProxy();
//		Admin admin = new Admin("3", "2", RoleEnum.APPADMIN, "3", "5", "6");
//		proxy.saveAdmin(admin);
//		System.out.println("amdin: " + proxy.getById(1).getType().equals(AdminRoleEnum.APPADMIN));
//		System.out.println("amdin: " + proxy.getById(1).getType().equals(AdminRoleEnum.PLATADMIN));
//		//System.out.print("amdins: " + proxy.getAllModels());
//		List<Admin> admins = (List<Admin>) proxy.findAll();
//		for (Admin am : admins) {
//			System.out.println("gu:" + am);
//		}
	}
	
	
	public static void main(String[] args) throws Exception {
		//initProxys();
		
		//fillin();		
		
		/*List<J2EEApp> apps = appProxy.getAllModels(false, false);
		for(J2EEApp app : apps) {
			System.out.println("app:  "+app);			
		}*/
		
		//close();
		
		testJPA();		
	}

}
