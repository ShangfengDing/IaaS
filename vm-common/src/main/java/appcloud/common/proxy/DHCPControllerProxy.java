package appcloud.common.proxy;

/**
 * @ClassName: DHCPControllerProxy
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-19 下午2:59:59
 */
public interface DHCPControllerProxy {
    /**
    * @Title: rebootService
    * @Description: 根据配置文件的内容重新加载dhcp服务
    * @param configurationFile
    */ 
    public void rebootService(String configurationFile);
}
