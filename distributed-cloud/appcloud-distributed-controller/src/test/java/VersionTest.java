//
//import appcloud.distributed.common.Constants;
//import appcloud.distributed.configmanager.SynInfo;
//import appcloud.distributed.configmanager.VersionInfo;
//import appcloud.distributed.configmanager.VersionInfoManager;
//import appcloud.distributed.process.operate.AccountOperate;
//import appcloud.distributed.process.operate.AccountOperateImpl;
//import appcloud.distributed.util.FileUtil;
//import appcloud.openapi.datatype.AppkeyItem;
//import com.distributed.common.utils.JsonUtil;
//import com.distributed.common.utils.ModelUtil;
//import com.distributed.common.utils.UuidUtil;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.List;
//
///**
// * Created by Idan on 2018/3/19.
// * 版本控制测试
// */
//public class VersionTest {
//
//    private AccountOperate accountOperate = AccountOperateImpl.getInstance();
//    private String requestId = UuidUtil.getRandomUuid();
//    VersionInfoManager versionInfoManager = VersionInfoManager.getInstance();
//    AppkeyItem tempItem = new AppkeyItem(16, 10349, "yumike18@163.com", "e2bee8e97fb84aa98414cc6462cedd7d", "DJ8zzA1TDZjKJfSgltcGeWrTSneTr2pq", "yunhai", "云海");
//    private String filePath = Constants.VERSION_PATH + File.separator + Constants.VERSION_FILE_NAME;
//
//
//    /**
//     * 1. 存储文件
//     */
//    @Test
//    public void testStore() {
//        VersionInfo versionInfo = accountOperate.addUserVersion(requestId, tempItem);
//        versionInfoManager.storeVersionToLog(versionInfo);
//
////        VersionInfo versionInfo2 = accountOperate.addUserVersion(requestId, tempItem);
////        versionInfoManager.storeVersionToLog(versionInfo2);
//
//    }
//
//    /**
//     * 2. 根据version恢复
//     */
//    @Test
//    public void recoveryVersion() {
//        List<String> versionList = FileUtil.getLines(filePath, 3);
//        for (String line : versionList) {
//            System.out.println(line);
//        }
//        String versionLine = versionList.get(0);
//
//        VersionInfo versionInfo = JsonUtil.gsonToObject(versionLine, VersionInfo.class);
//        System.out.println(versionInfo);
//
//        if (ModelUtil.isNotEmpty(versionInfo)) {
//            // 开始分发
//            List<SynInfo> infoList = versionInfo.getSynInfos();
//            SynInfo synInfo = infoList.get(0);
//            versionInfoManager.recoveryByInfo(synInfo);
//        }
//    }
//
//    @Test
//    public void testBean() {
//       /* String service ="com.distributed.common.service.db.AccountService";
//        String factory = "com.distributed.common.factory.DbFactory";
//        String methodName = "getAccountService";
//        try {
//            Class<?> factoryClass = ReflectionUtil.getClass(factory);
//            Method[] methods = factoryClass.getDeclaredMethods();
//            for (Method method : methods) {
//                if (method.getName().equals(methodName)) {
//                    Object instance = method.invoke(factoryClass, null);
////                    System.out.println(instance);
//                    Class<?> accountService = ReflectionUtil.getClass(service);
//                    Method[] accountMethods = accountService.getMethods();
//                    for (Method requestMethod: accountMethods) {
//                        if (requestMethod.getName().equals("addAppkey")) {
//                            String temp = "{\"id\":16,\"userId\":10349,\"userEmail\":\"yumike18@163.com\",\"appkeyId\":\"e2bee8e97fb84aa98414cc6462cedd7d\",\"appkeySecret\":\"DJ8zzA1TDZjKJfSgltcGeWrTSneTr2pq\",\"provider\":\"yunhai\",\"appname\":\"云海\",\"region\":null,\"zone\":null,\"appcloudEmail\":null,\"state\":null}";
//                            Appkey appkey = JsonUtil.gsonToObject(temp, Appkey.class);
//                            System.out.println(appkey);
//                            Object[] args = {appkey};
//                            Object result = requestMethod.invoke(instance, args);
//                            System.out.println(result);
//                        }
//                    }
//
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
////        AccountService accountService = null;
////        accountService = DbFactory.getAccountService( );
////        System.out.println(accountService.findByZoneIdAndEmail("zpark","free@free4lab.com"));
//    }
//
//}
