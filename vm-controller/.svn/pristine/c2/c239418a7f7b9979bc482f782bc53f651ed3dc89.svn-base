package appcloud.vmcontroller.virt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;


public class NetworkFilterConfigTest {
	@Test
	public void xmlTest() {
		VmSecurityGroup sg = new VmSecurityGroup();
		sg.setName("test");
		
		NetworkFilterConfig nfConf  = new NetworkFilterConfig(sg);
		
		List<VmSecurityGroupRule> rules = new ArrayList<VmSecurityGroupRule>();
		
		rules.add(new VmSecurityGroupRule(1, 3, "", "tcp", 22, 22, "192.168.0.0/16", "192.168.1.22", "192.168.1.65", "in", "", "", "", 123));
		rules.add(new VmSecurityGroupRule(1, 3, "", "tcp", 80, 80, "0.0.0.0/0", "192.168.1.22", "192.168.1.65", "in", "", "", "", 123));
		
		System.out.println(nfConf.generateFilterConf(rules));
	}

}
