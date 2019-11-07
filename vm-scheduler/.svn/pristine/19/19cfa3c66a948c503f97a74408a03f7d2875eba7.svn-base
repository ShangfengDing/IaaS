package appcloud.vmscheduler.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmImage.VmImageOSModeEnum;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;

public class VMSchedulerImplTest {
	public VMSchedulerImpl service = new VMSchedulerImpl();

	@Test
	public void testVMSchedulerImpl() throws Exception {
		VmInstance instance = new VmInstance();
		String uuid = "da73c472243f4950ba2add2c5e8604c3";
        instance.setUuid(uuid);
        instance.setName("test-yhw");
        instance.setOsType(VmImageOSTypeEnum.LINUX);
        instance.setRootDeviceLocation("/dev/vda");
        instance.setOsMode(VmImageOSModeEnum.HVM);

        String instanceType = "TestReservedFlavorUuid1";
        

        List<VmInstanceMetadata> instanceMetadata = new ArrayList<VmInstanceMetadata>();

        List<VmVolume> volume = new ArrayList<VmVolume>();
//        VmVolume v1 = new VmVolume();
//        v1.setVolumeUuid("VolumeTestReserved1");
//        v1.setProviderLocation("192.168.1.67:/srv/nfs4:images/c/e/rhel.img");
        VmVolumeProxy volumeProxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
        VmVolume v = volumeProxy.getById(3, false, false, false, false);
        volume.add(v);

        List<VmSecurityGroupRule> securityGroupRules = new ArrayList<VmSecurityGroupRule>();

        List<VmVirtualInterface> vifs = new ArrayList<VmVirtualInterface>();
        VmVirtualInterface vif1 = new VmVirtualInterface();
        vif1.setNetworkType("private");
        vif1.setAddress(" 192.168.15.20");
        vif1.setMac("00:1C:25:D9:A6:21");
        //VmVirtualInterface vif2= new VmVirtualInterface();
        //vif2.setNetworkType("public");
        //vif2.setAddress("10.109.247.150");
        //vif2.setMac("00:1C:25:D9:A6:25");
        vifs.add(vif1);
        //vifs.add(vif2);
        
        List<VmInstanceMetadata> vims = new ArrayList<VmInstanceMetadata>();
        VmInstanceMetadata vim1= new VmInstanceMetadata();
        vim1.setKey("maxBandwidth");
        vim1.setValue("24");
        vims.add(vim1);
//		try {
//			service.createVM(uuid, instanceType, instanceMetadata, volume, securityGroupRules, vifs);
//		} catch (RpcException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Test
	public void testCreateVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testRebootVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testSuspendVM() {
		fail("Not yet implemented");
	}

	@Test
	public void testResumeVM() {
		fail("Not yet implemented");
	}

//	public void testSelectHost() throws RpcException {
//		List<Host> list = service.selectHost("da73c472243f4950ba2add2c5e8604c3", "TestReservedFlavorUuid1", 1, null);
//		if (list == null)
//			System.out.println("here");
//		for (Host h : list) {
//			System.out.println(h.getUuid());
//		}
//	}

	@Test
	public void testAttachVolume() {
		fail("Not yet implemented");
	}

}
