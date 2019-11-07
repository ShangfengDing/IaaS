package appcloud.admin.action.runtime;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;

public class PreChangePasswordAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(PreChangePasswordAction.class);
	private Integer id;
	
	@Override
	public String execute() throws Exception {
		logger.info(id);
		//no use
		return SUCCESS;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
