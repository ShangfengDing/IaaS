package appcloud.admin.action.img;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.api.client.ImageClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.admin.action.base.BaseAction;

public class DelImgAction extends BaseAction {
	/**
	 * 删除模板
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DelImgAction.class);
	private ImageClient imageClient = ClientFactory.getImageClient();
	private String imageId = "";
	private String groupId = "";
	private String result = "fail";

	public String execute(){
		logger.info("delete Image imageId:"+imageId+" groupId:"+groupId);
		String imageName = imageClient.get(Constants.ADMIN_TENANT_ID, imageId).name;
		if(imageClient.deleteImage(Constants.ADMIN_TENANT_ID, imageId,groupId)){
			
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除模板", "删除模板"+imageName, "DelImgAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			this.setResult("success");
			logger.info("delete done result:"+result);
			return SUCCESS;
		}else{
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
