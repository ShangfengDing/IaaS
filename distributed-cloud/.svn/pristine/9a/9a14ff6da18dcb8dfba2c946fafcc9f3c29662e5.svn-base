package appcloud.distributed.process.thread;

import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.response.CreateInstanceResponse;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.entity.InstanceBackInfo;
import com.distributed.common.entity.VmBack;
import com.distributed.common.entity.VmImageBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Idan on 2018/6/8.
 */
public class RecoveryThread{

    private static final Logger logger = LoggerFactory.getLogger(RecoveryThread.class);

    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private String regionId;
    private String zoneId;
    private VmBack vmBack;

    public RecoveryThread(VmBack vmBack, String regionId, String zoneId) {
        this.vmBack = vmBack;
        this.regionId = regionId;
        this.zoneId = zoneId;
    }

    public void run() {
        InstanceBackInfo vmInfo = vmBack.getInstanceBackInfo();
        VmImageBack imageBack = vmBack.getVmImageBack();
        if (vmInfo ==  null || imageBack == null) {
            logger.info("the vm info is null, the imageBack is null");
        }
        try {
            logger.info("recovery the instance, vmInfo:"+vmInfo+"\r\n imageBack:"+imageBack);
            //TODO 2018-6-13 此处的instanceType 应该各不相同
            CreateInstanceResponse response = instanceClient.RecoveryInstance(regionId, zoneId, imageBack.getVolumeUuid(), vmInfo.getImageUuid(), "free.t1.small",
                    "5b82166e8b7244b9b81cb78d8a90ba1c", vmInfo.getDisplayName(), vmInfo.getDisplayDescription(), vmInfo.getInstanceChargeType(),
                    vmInfo.getInstanceChargeLength(), vmInfo.getInternetChargeType(), null, vmBack.getDestHostUuid(), null,
                    vmInfo.getSystemDiskSize(), vmInfo.getPriVmMac(), vmInfo.getPubVmMac(), vmInfo.getUserId(), vmInfo.getAppkeyId(), vmInfo.getAppkeySecret(), null);
            if (response.getMessage() != null) {
                logger.error("recovery the vm failed, "+response.getMessage());
            }
        } catch (Exception e) {
            logger.error("recovery the vm failed, the uuid is: "+vmBack.getUuid()+", "+e);
        }
    }
}