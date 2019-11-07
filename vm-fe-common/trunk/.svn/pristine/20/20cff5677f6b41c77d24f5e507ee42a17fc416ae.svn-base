package com.appcloud.vm.fe.help;

import org.json.JSONException;
import org.json.JSONObject;

import com.appcloud.vm.fe.common.Constants;

public class HelpContent {
	private Integer id;
	private String appId;
	private Integer chapter;
	private Integer section;
	private String title;
	private String content;
	private String uuid;
	
	public  HelpContent() {
    }
	
	public  HelpContent(String appid, Integer chapter, Integer section,  String title, String content,String uuid) {
    	this.chapter = chapter;
    	this.section = section;
    	this.appId = appid;
    	this.title = title;
    	this.content = content;
    	this.uuid=uuid;
    }
	
	public HelpContent(JSONObject obj) throws JSONException {
		this.id = Integer.parseInt(obj.getString("id"));
		this.appId = obj.getString("appId");
		this.chapter = Integer.parseInt(obj.getString("chapter"));
		this.section = Integer.parseInt(obj.getString("section"));
		this.title = obj.getString("title");
		this.content = obj.getString("content");
		this.uuid = obj.getString("uuid");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "HelpContent [id=" + id + ", appId=" + appId + ", chapter="
				+ chapter + ", section=" + section + ", title=" + title
				+ ", content=" + content + ", uuid=" + uuid + "]";
	}
	
	

}
