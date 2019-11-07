package com.appcloud.vm.action.sum;

import aliyun.openapi.client.*;
import appcloud.openapi.client.*;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.response.*;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeImagesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse.Region;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupsResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSnapshotsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.CompareDate;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.DiskStatus;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.manager.ServiceNumManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.model.ServiceNum;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

//import com.appcloud.vm.fe.manager.RegionManager;

public class SumAction extends BaseAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(SumAction.class);
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private VolumeClient diskClient = OpenClientFactory.getVolumeClient();
    private ImageClient imageClient = OpenClientFactory.getImageClient();
    private SnapshotClient snapshotClient = OpenClientFactory.getSnapshotClient();
    private SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();
    private AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
    private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
    private AliVolumeClient aliDiskClient = OpenClientFactory.getAliVolumeClient();
    private AliSnapshotClient aliSnapshotClient = OpenClientFactory.getAliSnapshotClient();
    private AliImageClient aliImageClient = OpenClientFactory.getAliImageClient();
    private AliSecurityGroupClient aliSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();
    private Integer userId = this.getSessionUserID();

    private AppkeyManager appkeyManager = new AppkeyManager();
    private ServiceNumManager serviceNumManager = new ServiceNumManager();
    private List<DiskStatus> diskServers = new ArrayList<DiskStatus>();
    private HashMap<Integer, String> zoneIdNameMap = new HashMap<Integer, String>();
    private HashMap<String, String> statusMap = new HashMap<String, String>();
    private TransformAttribute transform = new TransformAttribute();
//	private RegionManager regionManager = RegionManager.getInstance();

    //获取到的地域ID
    private List<Region> regionsAli = new ArrayList<Region>();
    private List<RegionItem> regionsYunhai = new ArrayList<RegionItem>();

    //查询各服务数量的最大的pageSize   
    private static final String MAX_PAGESIZE_ALI_INSTANCE = "50";
    private static final String MAX_PAGESIZE_ALI_DISK = "100";
    private static final String MAX_PAGESIZE_ALI_IMAGE = "100";
    private static final String MAX_PAGESIZE_ALI_SNAPSHOT = "100";
    private static final String MAX_PAGESIZE_YUNHAI_INSTANCE = "50";
    private static final String MAX_PAGESIZE_YUNHAI_DISK = "50";
    private static final String MAX_PAGESIZE_YUNHAI_IMAGE = "100";
    private static final String MAX_PAGESIZE_YUNHAI_SNAPSHOT = "100";

    //异步线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    //异步任务列表
    private List<FutureTask<Object>> futureTasks = new ArrayList<FutureTask<Object>>();


    //根据传入的type值来判断刷新哪一个设备服务数量
    private String type = null;


    // 用户云主机、云硬盘、快照、镜像数量显示
    private Integer instanceSum = null;
    private Integer instanceAcSum = null;
    private Integer instanceExpSum = null;
    private Integer diskSum = null;
    private Integer diskAcSum = null;
    private Integer diskExpSum = null;
    private Integer backupSum = null;//镜像
    private Integer shotSum = null;//快照
    private Integer securityGroupSum = null;//安全组规则
    private String balance = "0.00";

    private String diskRefTime = "";
    private String instanceRefTime = "";
    private String imageRefTime = "";
    private String snapshotRefTime = "";
    private String securityGroupRefTime = "";

    private String zoneId;

    private String result = "1";//1:成功，0:没有数据
    private Date nowDate = new Date();
    private CompareDate compareDate = new CompareDate();

    private List<Appkey> appkeyList; //账户信息

    public String execute() {
        initializeNum();
        appkeyList = appkeyManager.getAppkeyByUserId(userId);
        logger.info(appkeyList.size());
        for (Appkey appkey : appkeyList) {
            switch (appkey.getProvider()) {
                case Constants.YUN_HAI:
                    //addYunhaiDisk(appkey, diskServers);
                    getYunhaiRegions(appkey);
                    addYunhaiServiceNum(appkey);
                    break;
                case Constants.ALI_YUN:
                    //addAliyunDisk(appkey, diskServers);
                    getAliRegions(appkey);
                    addAliyunServiceNum(appkey);
                    break;
                case Constants.AMAZON:
                    //addAmazonDisk(appkey, diskServers);
                    addAmazonServiceNum(appkey);
                    break;
                default:
                    break;
            }
        }
        //zoneIdNameMap.put(null, "");
        executorService.shutdown();


        while (!executorService.isTerminated()) {

        }

        logger.info("获得所有server成功,开始更新数据库");
        serviceNumManager.updateServiceNum(userId, instanceSum, diskSum, backupSum,
                shotSum, securityGroupSum, instanceAcSum, diskAcSum);

        return SUCCESS;
    }

    /**
     * 根据type初始化各种数量参数，为了配合异步线程池。（最后更新数据库 NULL不给予更新）
     */
    private void initializeNum() {
        if ("all".equals(type)) {
            instanceSum = 0;
            instanceAcSum = 0;
            diskSum = 0;
            diskAcSum = 0;
            backupSum = 0;
            shotSum = 0;
            securityGroupSum = 0;
        } else if ("instance".equals(type)) {
            instanceSum = 0;
            instanceAcSum = 0;
        } else if ("disk".equals(type)) {
            diskSum = 0;
            diskAcSum = 0;
        } else if ("shot".equals(type)) {
            shotSum = 0;
        } else if ("image".equals(type)) {
            backupSum = 0;
        } else if ("security".equals(type)) {
            securityGroupSum = 0;
        }
    }


    /**
     * 根据前端返回的type类型，选择性的更新数据库信息。
     * 下面为云海账号的数量更新服务函数
     *
     * @param appkey
     */
    public void addYunhaiServiceNum(Appkey appkey) {
        if ("all".equals(type)) {
            FutureTask<Object> futureTaskInstance = (FutureTask<Object>) executorService.submit(new AddYunhaiInstance(appkey));
            futureTasks.add(futureTaskInstance);
            instanceRefTime = getStandardDate(new Date().getTime());

            FutureTask<Object> futureTaskDisk = (FutureTask<Object>) executorService.submit(new AddYunhaiDisk(appkey));
            futureTasks.add(futureTaskDisk);
            diskRefTime = getStandardDate(new Date().getTime());

            FutureTask<Object> futureTaskImage = (FutureTask<Object>) executorService.submit(new AddYunhaiImage(appkey));
            futureTasks.add(futureTaskImage);
            imageRefTime = getStandardDate(new Date().getTime());

            FutureTask<Object> futureTaskSecurity = (FutureTask<Object>) executorService.submit(new AddYunhaiSecurity(appkey));
            futureTasks.add(futureTaskSecurity);
            securityGroupRefTime = getStandardDate(new Date().getTime());

            FutureTask<Object> futureTaskShot = (FutureTask<Object>) executorService.submit(new AddYunhaiShot(appkey));
            futureTasks.add(futureTaskShot);
            snapshotRefTime = getStandardDate(new Date().getTime());
            /*更新数据库中对应账号的各种服务数量 */
/*            serviceNumManager.updateServiceNum(userId, instanceSum, diskSum, backupSum, 
                    shotSum, securityGroupSum, instanceAcSum, diskAcSum);*/
        }
        if ("instance".equals(type)) {
            FutureTask<Object> futureTaskInstance = (FutureTask<Object>) executorService.submit(new AddYunhaiInstance(appkey));
            futureTasks.add(futureTaskInstance);
            instanceRefTime = getStandardDate(new Date().getTime());
/*            serviceNumManager.updateServiceNum(userId, instanceSum, null, null, 
                    null, null, instanceAcSum, null);*/
        }
        if ("disk".equals(type)) {
            FutureTask<Object> futureTaskDisk = (FutureTask<Object>) executorService.submit(new AddYunhaiDisk(appkey));
            futureTasks.add(futureTaskDisk);
            diskRefTime = getStandardDate(new Date().getTime());
/*    		serviceNumManager.updateServiceNum(userId, null, diskSum, null, 
                    null, null, null, diskAcSum);*/
        }
        if ("shot".equals(type)) {
            FutureTask<Object> futureTaskShot = (FutureTask<Object>) executorService.submit(new AddYunhaiShot(appkey));
            futureTasks.add(futureTaskShot);
            snapshotRefTime = getStandardDate(new Date().getTime());
/*            serviceNumManager.updateServiceNum(userId, null, null, null, 
                    shotSum, null, null, null);*/
        }
        if ("image".equals(type)) {
            FutureTask<Object> futureTaskImage = (FutureTask<Object>) executorService.submit(new AddYunhaiImage(appkey));
            futureTasks.add(futureTaskImage);
            imageRefTime = getStandardDate(new Date().getTime());
  /*          serviceNumManager.updateServiceNum(userId, null, null, backupSum, 
            		null, null, null, null);*/
        }
        if ("security".equals(type)) {
            FutureTask<Object> futureTaskSecurity = (FutureTask<Object>) executorService.submit(new AddYunhaiSecurity(appkey));
            futureTasks.add(futureTaskSecurity);
            securityGroupRefTime = getStandardDate(new Date().getTime());
   /*         serviceNumManager.updateServiceNum(userId, null, null, null, 
            		null, securityGroupSum, null, null);*/
        }
    }

    /**
     * 根据前端返回的type类型，选择性的更新数据库信息。
     * 下面为阿里云账号的数量更新服务函数
     *
     * @param appkey
     */
    public void addAliyunServiceNum(Appkey appkey) {
        if ("all".equals(type)) {
/*    		addAliInstance(appkey,diskServers);
    		addAliDisk(appkey, diskServers);
    		//addAliImage(appkey, diskServers);
    		addAliSecurity(appkey, diskServers);
    		addAliShot(appkey, diskServers);*/
            FutureTask<Object> futureTaskInstance = (FutureTask<Object>) executorService.submit(new AddAliInstance(appkey));
            futureTasks.add(futureTaskInstance);

            FutureTask<Object> futureTaskDisk = (FutureTask<Object>) executorService.submit(new AddAliDisk(appkey));
            futureTasks.add(futureTaskDisk);

   /* 		FutureTask<Object> futureTaskImage = (FutureTask<Object>) executorService.submit(new AddAliImage(appkey));
    		futureTasks.add(futureTaskImage);*/

            /*FutureTask<Object> futureTaskSecurity = (FutureTask<Object>) executorService.submit(new AddAliSecurity(appkey));
            futureTasks.add(futureTaskSecurity);*/

            FutureTask<Object> futureTaskShot = (FutureTask<Object>) executorService.submit(new AddAliShot(appkey));
            futureTasks.add(futureTaskShot);
    		
/*            serviceNumManager.updateServiceNum(userId, instanceSum, diskSum, backupSum, 
            		shotSum, securityGroupSum, instanceAcSum, diskAcSum);*/
        }
        if ("instance".equals(type)) {
            //addAliInstance(appkey,diskServers);
            FutureTask<Object> futureTaskInstance = (FutureTask<Object>) executorService.submit(new AddAliInstance(appkey));
            futureTasks.add(futureTaskInstance);
    		
/*            serviceNumManager.updateServiceNum(userId, instanceSum, null, null, 
            		null, null, instanceAcSum, null);*/
        }
        if ("disk".equals(type)) {
            //addAliDisk(appkey, diskServers);
            FutureTask<Object> futureTaskDisk = (FutureTask<Object>) executorService.submit(new AddAliDisk(appkey));
            futureTasks.add(futureTaskDisk);
    		
/*            serviceNumManager.updateServiceNum(userId, null, diskSum, null, 
            		null, null, null, diskAcSum);*/
        }
        if ("shot".equals(type)) {
            //addAliShot(appkey, diskServers);
            FutureTask<Object> futureTaskShot = (FutureTask<Object>) executorService.submit(new AddAliShot(appkey));
            futureTasks.add(futureTaskShot);
    		
/*            serviceNumManager.updateServiceNum(userId, null, null, null, 
            		shotSum, null, null, null);*/
        }
        if ("image".equals(type)) {
            //addAliImage(appkey, diskServers);
    /* 		FutureTask<Object> futureTaskImage = (FutureTask<Object>) executorService.submit(new AddAliImage(appkey));
    		futureTasks.add(futureTaskImage);
    		
            serviceNumManager.updateServiceNum(userId, null, null, backupSum, 
            		null, null, null, null);*/
        }
        if ("security".equals(type)) {
            //addAliSecurity(appkey, diskServers);
            FutureTask<Object> futureTaskSecurity = (FutureTask<Object>) executorService.submit(new AddAliSecurity(appkey));
            futureTasks.add(futureTaskSecurity);
    		
   /*         serviceNumManager.updateServiceNum(userId, null, null, null, 
            		null, securityGroupSum, null, null);*/
        }
    }

    /**
     * 公共方法，获取regions,为后续查询数量提供参数
     *
     * @return
     */
    private void getYunhaiRegions(Appkey appkey) {
        //Appkey appkeyMan = appkeyManager.getAppkeyByUserEmail(Constants.MANAGER_EMAIL);
        //if (null == appkeyMan) return null;
        //List<RegionItem> regions = regionClient.DescribeRegions(appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), Constants.MANAGER_EMAIL).getRegionItems();
        regionsYunhai = regionClient.DescribeRegions(appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail()).getRegionItems();
//		List<com.appcloud.vm.fe.model.Region> regionList = regionManager.getRegionList();
//		for(com.appcloud.vm.fe.model.Region region:regionList) {
//			RegionItem regionItem = new RegionItem(region.getRegionId());
//			regionsYunhai.add(regionItem);
//		}

        //if(regions == null) return null;
        //return regions;
    }

    private void getAliRegions(Appkey appkey) {
        //List<Appkey> appkeys = appkeyManager.getAppkeyByUserId(userId);
        // if (null == appkeyMan) return null;
        regionsAli = aliRegionClient.DescribeRegions(appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail()).getRegions();
        //if(regions == null) return null;
        // return regions;
    }


    /**
     *
     * 以下是云海获取各服务数量函数
     *
     */

    /**
     * 获取云海云主机数量
     * 以下所有方法均变为异步处理
     *
     * @param appkey
     * @param servers
     */

    class AddYunhaiInstance implements Callable<Object> {

        public Appkey appkey;

        public AddYunhaiInstance(Appkey appkey) {
            this.appkey = appkey;
        }

        @Override
        public Object call() throws Exception {
            for (RegionItem regionItem : regionsYunhai) {
                DescribeInstancesResponse instancetemp = instanceClient
                        .DescribeInstances(regionItem.getRegionId(), null,
                                null, null, null, null, null, null, null, null,
                                null, MAX_PAGESIZE_YUNHAI_INSTANCE,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                if (null != instancetemp) {
                    instanceSum += instancetemp.getTotalCount().intValue();
                    List<InstanceAttributes> Instances = instancetemp
                            .getInstances();
                    if (Instances != null && Instances.size() > 0) {
                        for (InstanceAttributes instance : Instances) {
                            logger.info("运行状态" + instance.getStatus());
                            if ("active".equals(instance.getStatus())) {
                                instanceAcSum++;
                            }
                        }
                    }

                    // 若数量大于100，需要多次在多页查询。
                    int searchTimes = (instancetemp.getTotalCount().intValue())
                            / Integer.parseInt(MAX_PAGESIZE_YUNHAI_INSTANCE);
                    for (int i = 1; i <= searchTimes; i++) {
                        DescribeInstancesResponse instanceList = instanceClient
                                .DescribeInstances(regionItem.getRegionId(),
                                        null, null, null, null, null, null,
                                        null, null, null,
                                        String.valueOf(i + 1),
                                        MAX_PAGESIZE_YUNHAI_INSTANCE,
                                        appkey.getAppkeyId(),
                                        appkey.getAppkeySecret(),
                                        appkey.getUserEmail());
                        List<InstanceAttributes> Instancesi = instanceList
                                .getInstances();
                        if (Instancesi != null && Instancesi.size() > 0) {
                            for (InstanceAttributes instance : Instancesi) {
                                logger.info("运行状态" + instance.getStatus());
                                if ("active".equals(instance.getStatus())) {
                                    instanceAcSum++;
                                }
								/*
								 * if (null != instance.getExpiredTime()) {
								 * Integer expiring =
								 * compareDate.compare_date(nowDate,
								 * instance.getExpiredTime()); if (expiring ==
								 * 1) { instanceExpSum++; } }
								 */
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * 获取云海磁盘的数量
     *
     * @param appkey
     * @param servers
     */
    class AddYunhaiDisk implements Callable<Object> {

        private Appkey appkey;


        public AddYunhaiDisk(Appkey appkey) {
            super();
            this.appkey = appkey;
        }

        @Override
        public Object call() throws Exception {
            for (RegionItem regionItem : regionsYunhai) {
                DisksDetailReponse disktemp = diskClient.DescribeDisks(
                        regionItem.getRegionId(), null, null, null, "NETWORK",
                        null, null, null, null, null, null, null,
                        MAX_PAGESIZE_YUNHAI_DISK, null, appkey.getAppkeyId(),
                        appkey.getAppkeySecret(), appkey.getUserEmail());
                if (null != disktemp) {
                    diskSum += (int) disktemp.getTotalCount();
                    List<DiskDetailItem> disks = disktemp.getDisks();
                    if (disks != null && disks.size() > 0) {
                        for (DiskDetailItem disk : disks) {
                            if (!"SYSTEM".equals(disk.getDiskType())) {
                                if ("INUSE".equals(disk.getStatus())) {
                                    diskAcSum++;
                                }
                            }
                        }
                    }

                    // 若数量大于100，需要多次在多页查询。
                    int searchTimes = ((int) disktemp.getTotalCount())
                            / Integer.parseInt(MAX_PAGESIZE_YUNHAI_DISK);
                    for (int i = 1; i <= searchTimes; i++) {
                        DisksDetailReponse diskList = diskClient.DescribeDisks(
                                regionItem.getRegionId(), null, null, null,
                                "NETWORK", null, null, null, null, null, null,
                                String.valueOf(i + 1),
                                MAX_PAGESIZE_YUNHAI_DISK, null,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                        List<DiskDetailItem> disksi = diskList.getDisks();
                        if (disksi != null && disksi.size() > 0) {
                            for (DiskDetailItem disk : disksi) {
                                if (!"SYSTEM".equals(disk.getDiskType())) {
                                    if ("INUSE".equals(disk.getStatus())) {
                                        diskAcSum++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }

    }

    /**
     * 获取云海快照的数量
     *
     * @param appkey
     * @param servers
     */
    class AddYunhaiShot implements Callable<Object> {
        private Appkey appkey;

        public AddYunhaiShot(Appkey appkey) {
            this.appkey = appkey;
        }

        @Override
        public Object call() throws Exception {
            for (RegionItem regionItem : regionsYunhai) {
                SnapshotsDetailReponse ShotList = snapshotClient
                        .DescribeSnapshot(regionItem.getRegionId(), zoneId, null, null,
                                null, null, null, null, null, null, null, null,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                if (null != ShotList) {
                    shotSum += (int) ShotList.getTotalCount();
                }
            }
            return shotSum;
        }
    }

    /**
     * 获取云海镜像的数量
     *
     * @param appkey
     * @param servers
     */
    class AddYunhaiImage implements Callable<Object> {

        public Appkey appkey;

        public AddYunhaiImage(Appkey appkey) {
            this.appkey = appkey;
        }

        @Override
        public Object call() throws Exception {
            for (RegionItem regionItem : regionsYunhai) {
                GetImageListResponse getImageListResponse = imageClient
                        .DescribeImages(regionItem.getRegionId(), null, null, null,
                                null, null, null, null, null, "PRIVATE", null, null, null, null, null, null,
                                null, appkey.getAppkeyId(),
                                appkey.getAppkeySecret(), appkey.getUserEmail());
                backupSum += getImageListResponse.getTotalCount();
            }
            return backupSum;
        }
    }

    /**
     * 获取云海安全规则的数量
     *
     * @param appkey
     * @param servers
     */
    class AddYunhaiSecurity implements Callable<Object> {

        public Appkey appkey;

        public AddYunhaiSecurity(Appkey appkey) {
            this.appkey = appkey;
        }

        @Override
        public Object call() throws Exception {
            for (RegionItem regionItem : regionsYunhai) {
                SecurityGroupsDetailReponse securityGroupsDetailReponse = securityGroupClient
                        .DescribeSecurityGroups(regionItem.getRegionId(), zoneId, null,
                                null, null, appkey.getAppkeyId(),
                                appkey.getAppkeySecret(), appkey.getUserEmail());
                if (null != securityGroupsDetailReponse) {
                    securityGroupSum += (int) securityGroupsDetailReponse
                            .getTotalCount();
                }
            }
            return securityGroupSum;
        }

    }

    /**
     * 阿里云的各项服务数量获取函数
     * 所有的访问必须通过传入用户的appkey中的参数
     * 以下均为异步处理
     */

    /**
     * 获取阿里云主机的信息
     *
     * @param appkey
     * @param servers
     */
    class AddAliInstance implements Callable<Object> {

        public Appkey appkey;

        public AddAliInstance(Appkey appkey) {
            super();
            this.appkey = appkey;
        }

        public Object call() throws Exception {
            for (Region region : regionsAli) {
                // 查询所有云主机,先获得总数量，再用总数量去查询
                com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse instancetemp = aliInstanceClient
                        .DescribeInstances(region.getRegionId(), null, null,
                                null, null, null, null, null, null, null, null,
                                MAX_PAGESIZE_ALI_INSTANCE,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                if (null != instancetemp) {
                    instanceSum += instancetemp.getTotalCount();
                    List<Instance> Instances = instancetemp.getInstances();
                    if (Instances != null && Instances.size() > 0) {
                        for (Instance instance : Instances) {
                            logger.info("ali运行状态" + instance.getStatus());
                            if ("Running".equals(instance.getStatus()
                                    .getStringValue())) {
                                instanceAcSum++;
                            }
                        }
                    }

                    // 接下来查询正在运行中的磁盘数量，若数量大于100，需要多次在多页查询。
                    int searchTimes = (instancetemp.getTotalCount())
                            / Integer.parseInt(MAX_PAGESIZE_ALI_INSTANCE);
                    for (int i = 1; i <= searchTimes; i++) {
                        com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse instanceList = aliInstanceClient
                                .DescribeInstances(region.getRegionId(), null,
                                        null, null, null, null, null, null,
                                        null, null, String.valueOf(i + 1),
                                        MAX_PAGESIZE_ALI_INSTANCE,
                                        appkey.getAppkeyId(),
                                        appkey.getAppkeySecret(),
                                        appkey.getUserEmail());
                        List<Instance> Instancesi = instanceList.getInstances();
                        if (Instancesi != null && Instancesi.size() > 0) {
                            for (Instance instance : Instancesi) {
                                logger.info("运行状态" + instance.getStatus());
                                if ("Running".equals(instance.getStatus()
                                        .getStringValue())) {
                                    instanceAcSum++;
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * 获取阿里云硬盘的信息
     *
     * @param appkey
     * @param servers
     */
    class AddAliDisk implements Callable<Object> {

        public Appkey appkey;

        public AddAliDisk(Appkey appkey) {
            this.appkey = appkey;
        }

        public Object call() throws Exception {
            for (Region region : regionsAli) {
                // 查询阿里云磁盘实例
                DescribeDisksResponse disktemp = aliDiskClient.DescribeDisks(
                        region.getRegionId(), null, null, null, null, null,
                        null, null, null, null, MAX_PAGESIZE_ALI_DISK, null,
                        null, appkey.getAppkeyId(), appkey.getAppkeySecret(),
                        appkey.getUserEmail());
                if (null != disktemp) {
                    diskSum += (int) disktemp.getTotalCount();
                    List<Disk> disks = disktemp.getDisks();
                    if (disks != null && disks.size() > 0) {
                        // 为何是System?
                        for (Disk disk : disks) {
                            if (!"SYSTEM".equals(disk.getType())) {
                                if ("In_use".equals(disk.getStatus())) {
                                    diskAcSum++;
                                }
                            }
                        }
                    }

                    // 接下来查询正在运行中的磁盘数量，若数量大于100，需要多次在多页查询。
                    int searchTimes = (disktemp.getTotalCount())
                            / Integer.parseInt(MAX_PAGESIZE_ALI_DISK);
                    for (int i = 1; i <= searchTimes; i++) {
                        DescribeDisksResponse diskList = aliDiskClient
                                .DescribeDisks(region.getRegionId(), null,
                                        null, null, null, null, null, null,
                                        null, String.valueOf(i + 1),
                                        MAX_PAGESIZE_ALI_DISK, null, null,
                                        appkey.getAppkeyId(),
                                        appkey.getAppkeySecret(),
                                        appkey.getUserEmail());
                        List<Disk> disksi = diskList.getDisks();
                        if (disksi != null && disksi.size() > 0) {
                            // 为何是System?
                            for (Disk disk : disksi) {
                                if (!"SYSTEM".equals(disk.getType())) {
                                    if ("In_use".equals(disk.getStatus())) {
                                        diskAcSum++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * 获取阿里云快照
     *
     * @param appkey
     */
    class AddAliShot implements Callable<Object> {

        public Appkey appkey;

        public AddAliShot(Appkey appkey) {
            this.appkey = appkey;
        }

        public Object call() throws Exception {
            for (Region region : regionsAli) {
                DescribeSnapshotsResponse ShotList = aliSnapshotClient
                        .DescribeSnapshot(region.getRegionId(), null, null,
                                null, null, null, null, null, null, null, null,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                if (null != ShotList) {
                    shotSum += (int) ShotList.getTotalCount();
                }
            }
            return shotSum;
        }
    }

    /**
     * 获取阿里云镜像
     *
     * @param appkey
     */
    class AddAliImage implements Callable<Object> {

        public Appkey appkey;

        public AddAliImage(Appkey appkey) {
            this.appkey = appkey;
        }

        public Object call() throws Exception {
            for (Region region : regionsAli) {
                DescribeImagesResponse getImageListResponse = aliImageClient
                        .DescribeImages(region.getRegionId(), null, null, null,
                                null, null, null, null, null, null, null,
                                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                                appkey.getUserEmail());
                backupSum += getImageListResponse.getTotalCount();
            }
            return backupSum;
        }
    }

    /**
     * 获取阿里云安全规则数量
     *
     * @param appkey
     */

    class AddAliSecurity implements Callable<Object> {
        public Appkey appkey;

        public AddAliSecurity(Appkey appkey) {
            this.appkey = appkey;
        }


        public Object call() throws Exception {
            for (Region region : regionsAli) {
                DescribeSecurityGroupsResponse securityGroupsDetailReponse;
                try {
                    securityGroupsDetailReponse = aliSecurityGroupClient
                            .DescribeSecurityGroups(region.getRegionId(), null,
                                    null, null, appkey.getAppkeyId(),
                                    appkey.getAppkeySecret(),
                                    appkey.getUserEmail());
                    if (null != securityGroupsDetailReponse) {
                        securityGroupSum += (int) securityGroupsDetailReponse
                                .getTotalCount();
                    }
                } catch (ClientException e) {
                    logger.info("获取云安全失败");
                }

            }
            return securityGroupSum;
        }
    }


    public void addAmazonServiceNum(Appkey appkey) {

    }

    /**
     * 进入概览页面通过查询数据库获取各种服务的数量
     *
     * @return
     */
    public String addAllServiceNum() {
        appkeyList = appkeyManager.getAppkeyByUserId(userId);
        ServiceNum serviceNum = serviceNumManager.getServiceByUserId(userId);
        if (serviceNum != null) {
            instanceSum = serviceNum.getInstanceNum();
            diskSum = serviceNum.getDiskNum();
            shotSum = serviceNum.getSnapshotNum();
            //securityGroupSum = serviceNum.getSecuritygroupNum();
            backupSum = serviceNum.getImageNum();
            instanceAcSum = serviceNum.getInstanceAcNum();
            diskAcSum = serviceNum.getDiskAcNum();

            diskRefTime = getStandardDate(serviceNum.getDiskRefTime());
            instanceRefTime = getStandardDate(serviceNum.getInstanceRefTime());
            imageRefTime = getStandardDate(serviceNum.getImageRefTime());
            snapshotRefTime = getStandardDate(serviceNum.getSnapshotRefTime());
            securityGroupRefTime = getStandardDate(serviceNum.getSecuritygroupRefTime());

            double balanceDouble = 0;
            balanceDouble = BillingAPI.balance(this.getSessionUserID(), this.getAccessToken(), null, null) / 100.0;
            DecimalFormat decimalFormat = new DecimalFormat("###0.00");//格式化设置
            balance = decimalFormat.format(balanceDouble);
        }
        return SUCCESS;
    }


    /**
     * 获取形如：1993-7-19 12:31:23这种格式的时间
     *
     * @param date
     * @return
     */

    public String getStandardDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    public String getStandardDate(Date date) {
        if (date == null)
            return "";
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date.getTime());
            return str;
        }
    }


    public List<DiskStatus> getDiskServers() {
        return diskServers;
    }

    public HashMap<Integer, String> getZoneIdNameMap() {
        return zoneIdNameMap;
    }

    public HashMap<String, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(HashMap<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    public Integer getInstanceSum() {
        return instanceSum;
    }

    public void setInstanceSum(Integer instanceSum) {
        this.instanceSum = instanceSum;
    }

    public Integer getInstanceAcSum() {
        return instanceAcSum;
    }

    public void setInstanceAcSum(Integer instanceAcSum) {
        this.instanceAcSum = instanceAcSum;
    }

    public Integer getInstanceExpSum() {
        return instanceExpSum;
    }

    public void setInstanceExpSum(Integer instanceExpSum) {
        this.instanceExpSum = instanceExpSum;
    }

    public Integer getDiskSum() {
        return diskSum;
    }

    public void setDiskSum(Integer diskSum) {
        this.diskSum = diskSum;
    }

    public Integer getDiskAcSum() {
        return diskAcSum;
    }

    public void setDiskAcSum(Integer diskAcSum) {
        this.diskAcSum = diskAcSum;
    }

    public Integer getDiskExpSum() {
        return diskExpSum;
    }

    public void setDiskExpSum(Integer diskExpSum) {
        this.diskExpSum = diskExpSum;
    }

    public Integer getBackupSum() {
        return backupSum;
    }

    public void setBackupSum(Integer backupSum) {
        this.backupSum = backupSum;
    }

    public Integer getShotSum() {
        return shotSum;
    }

    public void setShotSum(Integer shotSum) {
        this.shotSum = shotSum;
    }

    public Integer getSecurityGroupSum() {
        return securityGroupSum;
    }

    public void setSecurityGroupSum(Integer securityGroupSum) {
        this.securityGroupSum = securityGroupSum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<Appkey> getAppkeyList() {
        return appkeyList;
    }

    public void setAppkeyList(List<Appkey> appkeyList) {
        this.appkeyList = appkeyList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDiskRefTime() {
        return diskRefTime;
    }

    public void setDiskRefTime(String diskRefTime) {
        this.diskRefTime = diskRefTime;
    }

    public String getInstanceRefTime() {
        return instanceRefTime;
    }

    public void setInstanceRefTime(String instanceRefTime) {
        this.instanceRefTime = instanceRefTime;
    }

    public String getImageRefTime() {
        return imageRefTime;
    }

    public void setImageRefTime(String imageRefTime) {
        this.imageRefTime = imageRefTime;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSnapshotRefTime() {
        return snapshotRefTime;
    }

    public void setSnapshotRefTime(String snapshotRefTime) {
        this.snapshotRefTime = snapshotRefTime;
    }

    public String getSecurityGroupRefTime() {
        return securityGroupRefTime;
    }

    public void setSecurityGroupRefTime(String securityGroupRefTime) {
        this.securityGroupRefTime = securityGroupRefTime;
    }
}
