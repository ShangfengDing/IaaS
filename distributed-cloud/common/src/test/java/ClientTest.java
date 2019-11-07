//import com.distributed.common.entity.CloudInfo;
//import com.distributed.common.entity.InstanceBackInfo;
//import com.distributed.common.entity.VmImageBack;
//import com.distributed.common.factory.ControllerFactory;
//import com.distributed.common.factory.DbFactory;
//import com.distributed.common.service.controller.AccountClient;
//import com.distributed.common.service.controller.BackUpVmClient;
//import com.distributed.common.service.controller.RouteInfoService;
//import com.distributed.common.service.db.VmImageBackService;
//import com.distributed.common.service.db.VmInstanceInfoService;
//import com.distributed.common.utils.UuidUtil;
//import org.junit.Test;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by Idan on 2018/3/11.
// * <p>
// * 单元测试的代码，其中service.json 文件也是不需要的
// */
//public class ClientTest {
//
//    private BackUpVmClient backUpVmClient = ControllerFactory.getBackUpVmClient();
//    private VmImageBackService vmImageBackService = DbFactory.getVmImageBackService();
//    private VmInstanceInfoService instanceInfoService = DbFactory.getVmInstanceInfoService();
//    private AccountClient accountClient = ControllerFactory.getAccountClient();
//    private RouteInfoService routeInfoService = ControllerFactory.getRouteInfoService();
//
//    String requestId = UuidUtil.getRandomUuid();
//    String regionId = "BEIJING";
//    String sourceDataCenter = "zpark";
//    String instanceUuid = "0f3c5d5383e64c88aa887373ebf96798";
//    String diskId = "a5fee72f970b479cb9febf416c07a3cc";
//    Boolean needClean = false;
//    String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
//    String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
//    String email = "free@free4lab.com";
//    String accountName = "slaveTest";
//    String groupSecretKey = "328dd3acb6d5408e88affd2e792f2177";
//
////    @Test
////    public void testBackUp() {
////        backUpVmClient.backUpVm(requestId, sourceDataCenter, instanceUuid, diskId, needClean, appkeyId, appkeySecret, email, accountName);
////    }
////
////    @Test
////    public void testVmImageBack() {
////        VmImageBack result = vmImageBackService.getByUuid(diskId);
////        System.out.println(result.toString());
////    }
////
////    @Test
////    public void testInstanceInfo() {
////        InstanceBackInfo instanceBackInfo = instanceInfoService.findByUuid(instanceUuid);
////        if (instanceBackInfo != null) {
////            System.out.println(instanceBackInfo.toString());
////        } else {
////            System.out.println("is null");
////        }
////    }
//
////    /**
////     * 测试创建用户
////     */
////    @Test
////    public void createUser() {
////        accountClient.userCreate(regionId, sourceDataCenter, "zh_air@163.com", groupSecretKey, appkeyId, appkeySecret, email, accountName);
////    }
//
//    @Test
//    public void testCloudInfo() {
//        List<CloudInfo> cloudInfoList = routeInfoService.findAllCloudInfo();
//        for (CloudInfo cloudInfo:cloudInfoList) {
//            System.out.println(cloudInfo.getUuid());
//        }
////        Set<String> ids = routeInfoService.findRegionIds();
////        for (String id:ids) {
////            System.out.println(ids);
////        }
//    }
////
////    @Test
////    public void findMonitor() {
////        CloudInfo cloudInfo = routeInfoService.findMonitorCloudInfo();
////        if (cloudInfo != null) {
////            System.out.println(cloudInfo.getUuid() + ":" + cloudInfo.getDataCenter());
////        }
////    }
//
//}
