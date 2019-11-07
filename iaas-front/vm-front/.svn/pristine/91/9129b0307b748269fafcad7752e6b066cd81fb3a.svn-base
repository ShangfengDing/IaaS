package com.appcloud.vm.action.help;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.log.SearchLogAction;
import com.appcloud.vm.fe.help.HelpContent;
import com.appcloud.vm.fe.help.HelpContentAPI;

public class ShowHelpContentAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ShowHelpContentAction.class);
	private HelpContent helpcontent;
	private String uuid;
	
	@Override
	public String execute() throws Exception {
		helpcontent = HelpContentAPI.getHelpContent(uuid);
		return SUCCESS;
	}
	
	public HelpContent getHelpcontent() {
		return helpcontent;
	}


	public void setHelpcontent(HelpContent helpcontent) {
		this.helpcontent = helpcontent;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	


	
	
}
