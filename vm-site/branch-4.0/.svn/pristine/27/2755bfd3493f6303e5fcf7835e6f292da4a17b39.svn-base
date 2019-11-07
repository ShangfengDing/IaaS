package com.appcloud.vm.action.account;

import org.apache.log4j.Logger;

import com.appcloud.vm.common.Constants;
import com.free4lab.utils.account.NewBaseLandingAction;

public class LandingAction extends NewBaseLandingAction {
	final static Logger logger = Logger.getLogger(LandingAction.class);

	@Override
	protected String getClientId() {
		return Constants.CLIENT_ID;
	}

	@Override
	protected String getRedirectUri() {
		return "/account/login";
	}
}