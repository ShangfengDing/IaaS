package appcloud.admin.action.img;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcGroup;
import appcloud.api.beans.Image;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.ImageClient;
import appcloud.api.enums.ImageTypeEnum;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImgListAction extends BaseAction {
	private Logger logger = Logger.getLogger(ImgListAction.class);
	private ImageClient imageClient = ClientFactory.getImageClient();
	private List<Image> images = new ArrayList<Image>();
	private ImageTypeEnum diskFormat = ImageTypeEnum.ALL;		//查询image还是iso
	private Map<Integer, String> groupIdName = new HashMap<Integer, String>();
	private List<AcGroup> group = new ArrayList<AcGroup>();
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();

	//多条件查询信息
	private String email;
	private String tenantId;
	private String groupId;
	private String type;  //只有image与iso的区别
	private String imageName;
	private String software;
	private String description;


	public String execute() {
		//TODO 暂时先获取全部模板，不考虑md5sum 
		//Constants.ADMIN_TENANT_ID 的ID默认为0，服务器端可以取得所有user_id的镜像
		images = imageClient.getImages(tenantId, true,groupId,diskFormat, type, email, Constants.QUERY_ALL,imageName,software,description);
		group = acGroupClient.getAcGroups();
		logger.info("type:"+diskFormat+",获取images成功");
		return SUCCESS;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setType(ImageTypeEnum diskFormat) {
		this.diskFormat = diskFormat;
	}


	public Map<Integer, String> getGroupIdName() {
		return groupIdName;
	}


	public void setGroupIdName(Map<Integer, String> groupIdName) {
		this.groupIdName = groupIdName;
	}


	public List<AcGroup> getGroup() {
		return group;
	}

	public void setGroup(List<AcGroup> group) {
		this.group = group;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
