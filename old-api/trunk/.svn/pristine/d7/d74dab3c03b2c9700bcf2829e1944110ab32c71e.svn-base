package appcloud.api.client;

import appcloud.api.beans.Image;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.enums.ImageTypeEnum;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;

public class ImageClient extends AbstractClient<Image>{
	
	public ImageClient(){
		super();
	}
	
	public ImageClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Image.class;
	}
	
	@Override
	protected GenericType<List<Image>> getGenericType() {		
		GenericType<List<Image>> type = new GenericType<List<Image>>() {};
		return type;
	}
	
	private String getPath() {
		return  "images";
	}
	
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	protected String buildPathWithId(String tenantId, String imageId) {
		return String.format("%s/%s", buildPath(tenantId), imageId);
	}
	protected String buildPathWithId(String tenantId, String imageId,String groupId) {
		return String.format("%s/%s/%s", buildPath(tenantId), groupId,imageId);
	}
	public Image get(String tenantId, String imageId) {
		return super.get(buildPathWithId(tenantId, imageId));
	}
	
	public List<Image> getImages(String tenantId, boolean detailed) {		
		if(detailed)
			return super.getList(buildPath(tenantId) + "/detail", null);
		return super.getList(buildPath(tenantId), null);
	}
	
	public List<Image> getImages(String  tenantId, boolean detailed, String groupId, ImageTypeEnum diskFormat,String type,  String email, String status, String imageName,String software,String description) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("groupId", groupId);
		if(diskFormat == null)
			params.add("diskFormat", ImageTypeEnum.ALL.toString());
		else
			params.add("diskFormat", diskFormat.toString());
		if (type == null) {
			params.add("type", "all");
		} else {
			params.add("type", type);
		}
		params.add("status", status);
		params.add("email",email);
		params.add("imageName",imageName);
		params.add("software",software);
		params.add("description",description);
		if(detailed)
			return this.getList(buildPath(tenantId) + "/detail", params);
		return super.getList(buildPath(tenantId), params);
	}
	
	public Image createImage (String tenantId, String serverId, String volumeId, 
			String displayName, String displayDescription, String groupIdList, String distribution) {

		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put(ImageMetadata.SERVER_ID, serverId);
		metadata.put(ImageMetadata.VOLUME_ID, volumeId);
		metadata.put(ImageMetadata.DISPLAY_NAME, displayName);
		metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, displayDescription);
		metadata.put(ImageMetadata.GROUP_ID_LIST, groupIdList);
		
		Image image = new Image();
		image.metadata = metadata;
		image.distribution = distribution;
		return super.postWithRet(buildPath(tenantId), image);
	}
	
	public boolean deleteImage(String tenantId, String imageId,String groupId) {
		return super.delete(buildPathWithId(tenantId, imageId, groupId));
	}

	// 授权某镜像是平台是公共镜像
	public Image authorizeImage(String tenantId, String imageUuid) {
		return super.get(buildPathWithId(tenantId, "authorize/"+imageUuid));
	}

	public Image updateImage(String tenantId, String imageId, String name, String discription) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put(ImageMetadata.DISPLAY_NAME, name);
		metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, discription);
		Image req = new Image();
		req.tenantId = tenantId;
		req.id = imageId;
		req.metadata = metadata;
		return super.update(buildPathWithId(tenantId, imageId), req);
	}
	
	public static void main(String[] args) {
//		ImageClient ic = new ImageClient();
//		ic.deleteImage("10", "d6fadc8148a94b2ab0eeb3d6ce312d87", "4");
//		List<Image> result = new ImageClient().getImages(null, true, null, ImageTypeEnum.ALL, "IMAGE", "all","shang", "hadoop", "des");
//		List<Image> result = new ImageClient().getImages(null, true, null, ImageTypeEnum.ALL, "IMAGE", "all", null, null , null);
//		Image rs = new ImageClient().authorizeImage("10","f86ad4f8f54841ae9b859b6f8c89f2d4");
	}
}
