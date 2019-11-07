package appcloud.api.manager.fake;

import appcloud.api.beans.Image;
import appcloud.api.manager.ImageManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FakeImageManager implements ImageManager {

	public List<Image> getList(String tenantId, boolean detailed, String groupId, String type, String status) {
		
				
		List<Image> images = new ArrayList<Image>();
		if(!detailed){
			images.add(new Image(tenantId, "image1", "dfsfd"));
			images.add(new Image(tenantId, "imgae2", "dfsfd"));
		}
		else {
			HashMap<String, String> metadata1 = new HashMap<String, String>();
			metadata1.put("displayName", "name_1");
			metadata1.put("displayDescription", "description_1");
			metadata1.put("isPublic", "true");
			Image image1 = new Image("image_1_id", "image_1_Name", tenantId, "image_1_status",
					200, 2048, 5, new Date(),metadata1);

			HashMap<String, String> metadata2 = new HashMap<String, String>();
			metadata2.put("displayName", "name_2");
			metadata2.put("displayDescription", "description_2");
			metadata2.put("isPublic", "false");
			Image image2 = new Image("image_2_id", "image_2_Name", tenantId, "image_2_status",
					200, 2048, 5, new Date(),metadata2);

			HashMap<String, String> metadata3 = new HashMap<String, String>();
			metadata3.put("displayName", "name_3");
			metadata3.put("displayDescription", "description_3");
			metadata3.put("isPublic", "true");
			Image image3 = new Image("image_3_id", "image_3_Name", tenantId, "image_3_status",
					200, 2048, 5, new Date(),metadata3);
			
			images.add(image1);
			images.add(image2);
			images.add(image3);
		}
		return images;
	}

	@Override
	public List<Image> getList(String tenantId, boolean detailed, String groupId, String diskFormat, String type, String email, String status, String imageName, String software, String description) throws Exception {
		return null;
	}

	@Override
	public Image get(String tenantId, String imageId) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put("displayName", "name_got");
		metadata.put("displayDescription", "description_got");
		metadata.put("isPublic", "true");
		Image image = new Image(imageId, "name_got", tenantId, "status_got",
				100, 1024, 4, new Date(),metadata);
		return image;
	}

	@Override
	public Image create(String tenantId, Image image) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put("displayName", "name_create");
		metadata.put("displayDescription", "description_create");
		metadata.put("isPublic", "false");
		Image imageCreated = new Image("image.id", "image.name", tenantId, "status_create",
				200, 2048, 5, new Date(),metadata);
		return imageCreated;
	}

	@Override
	public boolean authorizeImage(String tenantId, String imageUuid) throws Exception {
		return false;
	}

	public void delete(String tenantId, String imageId) {
		System.out.println("deleting " + tenantId + "'s image:" + imageId);
	}

	@Override
	public Image update(String tenantId, String imageId, Image image)
			throws Exception {
		System.out.println("updating " + tenantId + "'s image:" + imageId);
		
		HashMap<String, String> metadata = new HashMap<String, String>();
		HashMap<String, String> metaGot = image.metadata;
		metadata.put("displayName", metaGot.get("displayName"));
		metadata.put("displayDescription", metaGot.get("displayDescription"));
		metadata.put("isPublic", metaGot.get("isPublic"));
		Image imageUpdated = new Image(image.id, image.name, tenantId, "status_update",
				101, 2048, 6, new Date(),metadata);
		
		return imageUpdated;
	}

	@Override
	public boolean delete(String tenantId, String imageId, String groupId)
			throws Exception {
				return false;
		// TODO Auto-generated method stub
		
	}

}
