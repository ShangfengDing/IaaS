package appcloud.admin.action.img;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.api.client.ImageClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.api.enums.ImageTypeEnum;

import appcloud.admin.action.base.BaseAction;

public class ModImgAction extends BaseAction {
	/**
	 * 修改image信息
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ModImgAction.class);
	
	private ImageClient imageClient = ClientFactory.getImageClient();
	private ImageTypeEnum type = ImageTypeEnum.IMAGE;	//修改完成后跳转到img还是iso列表
	private String iid = "";			//要修改的模板id
	private String displayName = "";
	private String displayDescription = "";
	
	public String execute() {
		logger.info("修改模板iid:"+iid+",名称:"+displayName+",描述："+displayDescription);
		displayDescription = displayDescription.replaceAll("\r\n", "；");
		imageClient.updateImage(Constants.ADMIN_TENANT_ID, iid, displayName, displayDescription);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), iid, "修改模板", "修改模板"+imageClient.get(Constants.ADMIN_TENANT_ID, iid).name, "ModImgAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	
	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}


	public ImageTypeEnum getType() {
		return type;
	}

	public void setType(ImageTypeEnum type) {
		this.type = type;
	}

}
