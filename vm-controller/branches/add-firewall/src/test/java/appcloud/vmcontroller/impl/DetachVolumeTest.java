package appcloud.vmcontroller.impl;

import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;

public class DetachVolumeTest {
    
    public static void main(String[] args) throws Exception {
        AttachVolumeTest();
    }
    public static void AttachVolumeTest() throws Exception {
        VMControllerImpl vmControllerImpl = VMControllerImpl.getInstance();
        String instanceUUID = "b9dcd492-9bcb-1456-3938-1982a9746a12";
        VmVolume v1 = new VmVolume();
        v1.setName("qemu");
        v1.setProviderLocation("192.168.1.67:/srv/nfs4:volumes/g/u/yhw1.img");
        v1.setVolumeType(VmVolumeTypeEnum.QCOW2);
        v1.setMountPoint("hdc");
        v1.setBus("ide");
        
        vmControllerImpl.detachVolume(null,instanceUUID,v1);
    }
}
