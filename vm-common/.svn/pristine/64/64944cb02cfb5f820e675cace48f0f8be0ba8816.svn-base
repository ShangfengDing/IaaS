/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */

package appcloud.common.util;

import appcloud.common.errorcode.RSEC;

/**
 * 虚拟机异步控制工具
 * @author yicou
 */
public class VmNewControlUtils {

    static abstract class SaynRunner extends Thread {
        public SaynRunner(String uuid, Integer endState) {
            this.uuid = uuid;
            this.state = endState;
            this.start();
        }

        String uuid;
        Integer state;

        @Override
        public void run() {
            try {
                updateState(uuid, 2);
                doWork();
                updateState(uuid, state);
            } catch (Exception ex) {
                updateState(uuid, 3);
            }
        }

        protected void updateState(String uuid, Integer state) {
        }

        public abstract void doWork() throws Exception ;
    }

    /**
     * 购买虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid 虚拟机安装 uuid
     * @param templateId 虚拟机安装 templateId
     * @param mac 虚拟机安装 mac地址
     * @param ip 虚拟机安装 ip地址
     * @return App Master传回的响应
     * @throws Exception
     */
    public static void buyVM(final String epStr,
            final String uuid,
            final Integer templateId,
            final String mac,
            final String ip)
            throws Exception {
        // TODO: createVM bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
                if (ControlUtils.createVM(epStr, uuid, templateId, mac, ip).equals(RSEC.NO_ERROR)) {
                	ControlUtils.bootVM(epStr, uuid);
                }
            }
        };

        
    }

    /**
     * 启动虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static void bootVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
                ControlUtils.bootVM(epStr, uuid);
            }
        };
        
    }

    /**
     * 停止虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static void shutdownVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: shutdownVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
                ControlUtils.shutdownVM(epStr, uuid);
            }
        };
        
    }

    /**
     * 重启虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static void restartVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: shutdownVM bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
                if (ControlUtils.shutdownVM(epStr, uuid).equals(RSEC.NO_ERROR)) {
                	ControlUtils.bootVM(epStr, uuid);
                }
            }
        };
        
    }

    /**
     * 发布为模板
     * @param epStr
     * @param uuid
     * @param tempId 被发布的新模板id
     * @return
     * @throws Exception
     */
    public static void uploadTemplateVM(final String epStr,
            final String uuid,
            final Integer tempId)
            throws Exception {
        // TODO: shutdownVM uploadTemplateVM bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
            	RSEC result;
                result = ControlUtils.shutdownVM(epStr, uuid);
                if (!result.equals(RSEC.NO_ERROR)) return;
                result = ControlUtils.uploadTemplateVM(epStr, uuid, tempId);
                if (!result.equals(RSEC.NO_ERROR)) return;
                ControlUtils.bootVM(epStr, uuid);
            }
        };
        
    }

    /**
     * 修改硬件配置
     * @param epStr
     * @param uuid
     * @return
     * @throws Exception
     */
    public static void modifyVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: shutdownVM bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
            	RSEC result;
                result = ControlUtils.shutdownVM(epStr, uuid);
                if (!result.equals(RSEC.NO_ERROR)) return;
                ControlUtils.bootVM(epStr, uuid);
            }
        };
        
    }

    /**
     * 备份
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return
     * @throws Exception
     */
    public static void backupVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: shutdownVM backupVM bootVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
            	RSEC result;
                result = ControlUtils.shutdownVM(epStr, uuid);
                if (!result.equals(RSEC.NO_ERROR)) return;
                result = ControlUtils.backupVM(epStr, uuid);
                if (!result.equals(RSEC.NO_ERROR)) return;
                ControlUtils.bootVM(epStr, uuid);
            }
        };
        
    }


    /**
     * 停用虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static void deleteVM(final String epStr,
            final String uuid)
            throws Exception {
        // TODO: deleteVM
        new SaynRunner(uuid, 0) {
            @Override
            public void doWork() throws Exception {
            	RSEC result;
                result = ControlUtils.shutdownVM(epStr, uuid);
                if (!result.equals(RSEC.NO_ERROR)) return;
                ControlUtils.deleteVM(epStr, uuid);
            }
        };
        
    }



//    /**
//     * 网络硬盘：从当前虚拟机卸载/挂载到其他虚拟机
//     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
//     * @param uuid uuid
//     * @return App Master传回的响应
//     * @throws Exception
//     */
//    public static void reuseHD(final String epStr,
//            final String uuid,
//            final Integer tempId)
//            throws Exception {
//        // TODO: restartVM
//        
//    }
}
