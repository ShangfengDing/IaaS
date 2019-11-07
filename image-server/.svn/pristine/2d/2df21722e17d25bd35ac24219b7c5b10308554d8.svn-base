package appcloud.imageserver.impl;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ImageServerService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;

// 继承BTYunhaiService类
public class ImageServerManage extends BTYunhaiService{
	//包括routingkey，服务接口类，服务接口实例3个参数的构造函数
	public ImageServerManage(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}

	private static Logger logger = Logger.getLogger(ImageServerManage.class);
	
	public static void main(String args[]) throws Exception{
		logger.info("ImageServer starts!");
		//得到routingkey
        String routingkey = RoutingKeys.IMAGE_SERVER;

		//服务接口实例
		ImageServerImpl service = new ImageServerImpl();
				
		//由routingkey 接口类 接口实例 ，创建一个server实例
		ImageServerManage server = new ImageServerManage(routingkey, ImageServerService.class, service);

		//运行server
		server.run();	
	}
}
