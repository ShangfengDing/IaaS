package appcloud.api.resources;

import appcloud.api.beans.Image;
import appcloud.api.manager.ImageManager;

import javax.ws.rs.*;
import java.util.List;

@Path("{tenantId}/images")
public class ImageResource {
	private ImageManager imageManager;
	
	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
 
	
	@GET
	@Produces("application/xml")
	public List<Image> index(@PathParam("tenantId") String tenantId,
			@DefaultValue("all") @QueryParam("groupId") String groupId, 
			@DefaultValue("all") @QueryParam("diskFormat") String diskFormat,
			@DefaultValue("all") @QueryParam("status") String status,
								@QueryParam("type") String type,
							 	@QueryParam("imageName") String imageName,
							 	@QueryParam("software") String software,
							 	@QueryParam("description") String description) throws Exception {		return imageManager.getList(tenantId, false, groupId,diskFormat, type,null, status,imageName,software,description);	}
	
	@GET
	@Produces("application/xml")
	@Path("/detail")
	public List<Image> detail(@PathParam("tenantId") String tenantId,
			@DefaultValue("all") @QueryParam("groupId") String groupId,
			@DefaultValue("all") @QueryParam("diskFormat") String diskFormat, // 格式
			@DefaultValue("all") @QueryParam("status") String status,
							  @QueryParam("email") String email,
            @DefaultValue("all")  @QueryParam("type") String type,
							  @QueryParam("imageName") String imageName,
							  @QueryParam("software") String software,
							  @QueryParam("description") String description) throws Exception {
		return imageManager.getList(tenantId, true, groupId,diskFormat, type, email, status,imageName,software,description);
	}
	
	@POST
	@Consumes("application/xml")
	public Image create(@PathParam("tenantId") String tenantId, Image image) throws Exception {
		return imageManager.create(tenantId, image);
	}

	@GET
	@Path("/authorize/{imageUuid}")
	@Produces("application/xml")
	public Image authorizeImage(@PathParam("tenantId") String tenantId, @PathParam("imageUuid") String imageUuid) throws Exception {
		boolean rs = imageManager.authorizeImage(tenantId, imageUuid);
		System.out.println("authorize the image: "+ rs);
		return null;
	}
	
	@GET
	@Path("/{imageId}")
	@Produces("application/xml")
	public Image show(@PathParam("tenantId") String tenantId, @PathParam("imageId") String imageId) throws Exception {
		return imageManager.get(tenantId, imageId);
	}
	
	@DELETE
	@Path("/{groupId}/{imageId}")//,@PathParam("imageId") String imageId
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("groupId") String groupId
			, @PathParam("imageId") String imageId) throws Exception{
		System.out.println("in api-server delete get :"+imageId+groupId);
		boolean deleteFlag = imageManager.delete(tenantId, imageId, groupId);
		System.out.println("delete status:"+deleteFlag);
	}
	
	@PUT
	@Path("/{imageId}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Image update(@PathParam("tenantId") String tenantId, @PathParam("imageId") String imageId, 
			Image image) throws Exception{
		return imageManager.update(tenantId, imageId, image);
	}
	
}
