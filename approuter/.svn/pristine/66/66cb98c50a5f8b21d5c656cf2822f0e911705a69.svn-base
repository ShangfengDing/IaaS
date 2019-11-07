/**
 * File: AppRouterTestClient.java
 * Author: weed
 * Create Time: 2012-11-9
 */
package appcloud.approuter.amqp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.errorcode.AREC;
import appcloud.common.model.Domain;
import appcloud.common.model.Domain.DomainTypeEnum;
import appcloud.common.model.RoutingEntry;
import appcloud.common.proxy.DomainProxy;
import appcloud.common.proxy.RoutingEntryProxy;
import appcloud.common.service.AppRouterService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.ampq.BasicRPCClient;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 * 
 */
public class AppRouterTestClient {
	static DomainProxy domainProxy;
	static RoutingEntryProxy routingEntryProxy;
	static BasicRPCClient rpcClient;
	static AppRouterService service;
	static Scanner input=new Scanner(System.in);
	
	private static void pauseAndContinue(String function){
		System.out.println("Test " + function + " end. Continue?(y)");
		input.next();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("AppRouterTestClient is running!");

		domainProxy = ConnectionFactory.getDomainProxy();
		ConnectionFactory.getNginxProxy();// save time
		routingEntryProxy = ConnectionFactory.getRoutingEntryProxy();

		service =  ConnectionFactory.getAppRouter();


//		 basicTest();
//		testBatAdd_Delete();
//		testApply_Update_Update();
		testExample();
//		testPressure();
//		testExample();

		
		input.close();
		System.out.println("AppRouterTestClient is terminated!");
	}
	
	static void testBatAdd_Delete() throws Exception{
		Integer userId = new Integer(125);
		String srcPrefix = "approutertest";
		String srcSuffix = "free4lab.com";

		AREC ret1 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, srcPrefix, srcSuffix));
		System.out.println("applyDomain: " + ret1);
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
		
		AREC ret2 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, "approutertest", "free4home.com"));
		System.out.println("applyDomain: " + ret2);
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());

		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();

		for (int i=1; i<4; i++){
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
					"free4lab.com", "192.168.255." + i, "65535", i, 1));
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "approutertest",
					"free4lab.com", "nctest" + i, "free4lab.com", i, 1));
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
					"free4home.com", "192.168.0." + i, "65535", i, 1));
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "approutertest",
					"free4home.com", "testnc" + i, "free4home.com", i, 1));
		}



		AREC rets1 = service.addRoutingEntries(routingEntries);
		System.out.println("addRoutingEntries: " + rets1);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("addRoutingEntries");
		
		AREC ret3 = service.deleteAppRoutingEntries(srcPrefix, srcSuffix);
		System.out.println("deleteRoutingEntries: " + ret3);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("deleteAppRoutingEntries");
		
		AREC rets3 = service.deleteRoutingEntries(routingEntries);
		System.out.println("deleteRoutingEntries: " + rets3);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("deleteRoutingEntries");

	}

	static void testApply_Update_Update() throws Exception{
		Integer userId = new Integer(125);
		String srcPrefix = "approutertest";
		String srcSuffix = "free4lab.com";

		AREC ret1 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, srcPrefix, srcSuffix));
		System.out.println("applyDomain: " + ret1);

		
		for (int i=1; i<7; i++){
			AREC ret2 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, "nctest" + i, srcSuffix));
		}
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
		
		pauseAndContinue("applyDomain");

		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();
		for (int i=1; i<4; i++){
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
					"free4lab.com", "192.168.255." + i, "65535", i, 1));
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "approutertest",
					"free4lab.com", "nctest" + i, "free4lab.com", i, 1));
		}
		
		AREC rets1 = service.updateRoutingEntries(routingEntries);
		System.out.println("updateRoutingEntries: " + rets1);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("updateRoutingEntries");
		
		routingEntries.clear();
		for (int i=4; i<7; i++){
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
					"free4lab.com", "192.168.255." + i, "65535", i, 1));
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "approutertest",
					"free4lab.com", "nctest" + i, "free4lab.com", i, 1));
		}
		AREC rets2 = service.updateRoutingEntries(routingEntries);
		System.out.println("updateRoutingEntries: " + rets2);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("updateRoutingEntries");
		
		
		AREC rets3 = service.deleteDestRoutingEntries("nctest4", "free4lab.com");
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("updateRoutingEntries");
	}
	
	static void testPressure() throws Exception{
		
		
//		Integer userId = new Integer(125);
//		String srcPrefix = "approutertestpre";
//		String srcSuffix = "free4lab.com";

//		for (int i=0; i<1; i++){
////			Boolean ret1 = service.applyDomain(userId, srcPrefix + i, srcSuffix);
//			domainProxy.save(new Domain(null, userId, srcPrefix + i, srcSuffix));
//		}
//		System.out.println("applyDomain: " + ret1);
//		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
//		pauseAndContinue("applyDomain");
		
//		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();
//		
//		for (int i=0; i<128; i++){
//			for (int j=0; j<3; j++){
////				routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, srcPrefix + i * k,
////						"free4lab.com", "192.16" + k + "." + i + "." + j, "65535", 1, 1));
//			routingEntryProxy.save(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, srcPrefix + i,
//					"free4lab.com", "192.168." + i + "." + j, "65535", 1, 1));
//			}
			
//			System.out.println(routingEntries.toString().length());
//			List<Boolean> rets1 = service.addRoutingEntries(routingEntries);
//			pauseAndContinue("addRoutingEntries");
//			System.out.println("addRoutingEntries: " + rets1);
			System.out.println("RoutingEntries: "
					+ (List<RoutingEntry>)routingEntryProxy.findAll());
//		}

//		Boolean ret1 = service.applyDomain(125, "rsspubsub", "free4lab.com");
//		Boolean ret3 = service.addRoutingEntry(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "rsspubsub",
//				"free4lab.com", "192.168.10.224", "8090", 1, 1));
	}
	
	static void testExample() throws Exception{
		
		AREC ret1 = service.applyDomain(new Domain(null, 125, DomainTypeEnum.INSIDE, "rsspubsub", "free4lab.com"));
		System.out.println("applyDomain: " + ret1);
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
		pauseAndContinue("applyDomain");
		
		AREC ret2 = service.applyDomain(new Domain(null, 125, DomainTypeEnum.OUTSIDE, "domaintest", "free4lab.com"));
		System.out.println("applyDomain: " + ret2);
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
		pauseAndContinue("applyDomain");

		AREC ret3 = service.addRoutingEntry(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "rsspubsub",
				"free4lab.com", "192.168.10.224", "8090", 1, 1));
		System.out.println("addRoutingEntry: " + ret3);
		AREC ret4 = service.addRoutingEntry(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "domaintest",
				"free4lab.com", "rsspubsub", "free4lab.com", 1, 1));
		System.out.println("addRoutingEntry: " + ret4);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
		pauseAndContinue("addRoutingEntry");
		 
//		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();
//		routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
//				"free4lab.com", "127.0.0.1", "8080", 1, 1));
//		
//		routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.DOMAIN, "approutertest",
//				"free4lab.com", "rsspubsub", "free4lab.com", 1, 1));
//
//		AREC rets2 = service.updateRoutingEntries(routingEntries);
//		System.out.println("updateRoutingEntries: " + rets2);
//		System.out.println("RoutingEntries: "
//				+ (List<RoutingEntry>)routingEntryProxy.findAll());
//		pauseAndContinue("updateRoutingEntries");
	}
	
	static void basicTest() throws Exception {
		Integer userId = new Integer(125);
		String srcPrefix = "approutertest";
		String srcSuffix1 = "free4lab.com";
		String srcSuffix2 = "free4home.com";

		AREC ret1 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, srcPrefix, srcSuffix1));
		System.out.println("applyDomain: " + ret1);
		AREC ret2 = service.applyDomain(new Domain(null, userId, DomainTypeEnum.INSIDE, srcPrefix, srcSuffix2));
		System.out.println("applyDomain: " + ret2);
		System.out.println("Domains: " + (List<Domain>)domainProxy.findAll());
		

		RoutingEntry rc = new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, srcPrefix,
				srcSuffix1, "192.168.255.254", "65535", 0, 1);

		AREC rets1 = service.addRoutingEntry(rc);
		System.out.println("addRoutingEntries: " + rets1);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());

		AREC rets2 = service.addRoutingEntry(rc);
		System.out.println("addRoutingEntries: " + rets2);
		
		RoutingEntry gotrc = (RoutingEntry) routingEntryProxy.getBySrc(srcPrefix, srcSuffix1);

		AREC rets3 = service.deleteRoutingEntry(gotrc);
		System.out.println("deleteRoutingEntries: " + rets3);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());

		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();
		for (int i = 0; i < 5; i++) {
			routingEntries.add(new RoutingEntry(null, RoutingEntry.RETypeEnum.IP, "approutertest",
					srcSuffix2, "192.168.255." + i, "65535", 0, 1));
		}

		AREC rets4 = service.addRoutingEntries(routingEntries);
		System.out.println("addRoutingEntries: " + rets4);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());

		AREC ret3 = service.disableAppRoutingEntries("approutertest",
				srcSuffix2);
		System.out.println("disableAppRoutingEntries: " + ret3);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());

		AREC ret4 = service.deleteAppRoutingEntries("approutertest",
				srcSuffix2);
		System.out.println("deleteAppRoutingEntries: " + ret4);
		System.out.println("RoutingEntries: "
				+ (List<RoutingEntry>)routingEntryProxy.findAll());
	}
}
