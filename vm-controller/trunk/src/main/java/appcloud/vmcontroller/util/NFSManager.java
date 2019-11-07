package appcloud.vmcontroller.util;

import org.apache.log4j.Logger;

import appcloud.common.util.io.NFSUtil;

public class NFSManager {
    static final Logger logger = Logger.getLogger(NFSManager.class);

    /*
     * 挂载远端volume的预处理
     */
    public static boolean handleProviderLocation(String pl) {
        // v1.setProviderLocation("192.168.1.67:/srv/nfs4:volumes/a/z/uuid.img");

        // 1. 检查provider location是否为空
        if (pl == null) {
            logger.info("volume provider提供的provider location为空");
            return false;
        }

        String[] nfsList = pl.split(":", 3);
        /*
         * 2. 检查参数,检查provier location是否符合赋值规范: 如果符合,则赋值 如果不符合,则返回失败
         */
        String volumeHost = null; // 192.168.1.67
        String nfsRoot = null; // /srv/nfs4
        String volumeLocation = null; // volumes/a/z/uuid.img

        if ((nfsList[0] != null) && (nfsList[1] != null) && (nfsList[2] != null)) {
            volumeHost = nfsList[0]; // 192.168.1.67
            nfsRoot = nfsList[1]; // /srv/nfs4
            volumeLocation = nfsList[2]; // volumes/a/z/uuid.img
        } else {
            logger.info("volume provider的格式不符合要求：" + pl);
            return false;
        }

        logger.debug("volumeHost: " + volumeHost);
        logger.debug("nfsRoot: " + nfsRoot);
        logger.debug("volumeLocation: " + volumeLocation);

        /*
         * 挂载远端volume
         */
        if (!NFSUtil.isMounted(volumeHost)) {
            logger.debug("NFSUtil.isMounted(volumeHost) = " + NFSUtil.isMounted(volumeHost));
            if (!NFSUtil.mount(volumeHost, nfsRoot)) {
                logger.info("volume provider挂载到本地失败");
                return false;
            }
        }

        // 判断是否挂载到本地
        return NFSUtil.fileExists(volumeHost, volumeLocation);

    }

    /*
     * 获取挂载盘的本地路径
     */
    public static String getSourceFile(String pl) {
        String[] nfsList = pl.split(":", 3);
        String volumeHost = nfsList[0];
        String volumeLocation = nfsList[2];
        logger.info("sourceFile = " + NFSUtil.getPath(volumeHost, volumeLocation));
        return NFSUtil.getPath(volumeHost, volumeLocation);
    }

}
