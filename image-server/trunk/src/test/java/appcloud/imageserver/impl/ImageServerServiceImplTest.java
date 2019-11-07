package appcloud.imageserver.impl;

import org.apache.log4j.Logger;
import org.junit.Test;

import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.util.ConnectionFactory;

public class ImageServerServiceImplTest{
	private Logger logger = Logger.getLogger(ImageServerServiceImplTest.class);
	private ImageServerImpl serverImpl = new ImageServerImpl();

	VmImageProxy imageProxy = (VmImageProxy)ConnectionFactory.getDBProxy(VmImageProxy.class);
	ServiceProxy serviceProxy = (ServiceProxy)ConnectionFactory.getDBProxy(ServiceProxy.class);
	
	@Test
	public void test() throws Exception{
		VmImage image = new VmImage();
		image.setName("a.img");
		
		serverImpl.createImage(image,null);
		logger.info(image);
		
		image = serverImpl.getDownloadImage("uuid",null);
		logger.info(image);
	}
	
}
