package appcloud.api.manager;

import appcloud.api.beans.Image;

import java.util.List;

public interface ImageManager {

	/*public List<Image> getList(String tenantId, boolean detailed, String type, String status) throws Exception ;*/
	
	public List<Image> getList(String  tenantId, boolean detailed, String groupId, String diskFormat,String type, String email, String status,String imageName,String software,String description) throws Exception ;
	
	public Image get(String tenantId, String imageId) throws Exception ;
	
	public Image create(String tenantId, Image image) throws Exception ;

	public boolean authorizeImage(String tenantId, String imageUuid) throws Exception ;
	/*public List<Image> create(String tenantId, Image image) throws Exception ;*/
	
	public boolean delete(String tenantId, String imageId, String groupId) throws Exception ;
	
	public Image update(String tenantId, String imageId, Image image) throws Exception;
}
