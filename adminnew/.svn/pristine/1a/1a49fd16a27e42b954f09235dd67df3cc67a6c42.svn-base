package appcloud.admin.action.img;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Image;
import appcloud.api.client.ImageClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.UUID;

public class RelImgAction extends BaseAction {
	/**
	 * 发布模板
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(RelImgAction.class);
	private ImageClient imageClient = ClientFactory.getImageClient();
	private String imageId = "";
	private String result = "fail";

	public String execute(){
		logger.info("authorize Image imageId:"+imageId);
		String imageName = imageClient.get(Constants.ADMIN_TENANT_ID, imageId).name;
		try {
			Image image = imageClient.authorizeImage(Constants.ADMIN_TENANT_ID, imageId);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "发布模板", "发布模板"+imageName, "RelImgAction.class",
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			this.setResult("success");
			logger.info("authorize done result:"+result);
			return SUCCESS;
		} catch (Exception e) {
		    e.printStackTrace();
			return ERROR;
		}


	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
