package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AdminURL {
	private Integer id;
	private Integer resourceId;
	private String url;
	private String description;
	
	public AdminURL() {
		super();
	}
	public AdminURL(Integer id, Integer resourceId, String url,
			String description) {
		super();
		this.id = id;
		this.resourceId = resourceId;
		this.url = url;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "AdminURL [id=" + id + ", resourceId=" + resourceId + ", url="
				+ url + ", description=" + description + "]";
	}
}
