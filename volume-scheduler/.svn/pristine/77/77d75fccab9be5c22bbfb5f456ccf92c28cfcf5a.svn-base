package appcloud.vs.strategy.filter;

import appcloud.common.model.Host;

public class SameHostFilter extends BaseHostFilter {

	@Override
	protected boolean hostPass(Host host, Integer size, Host exHost) {
		if (host.getId().equals(exHost.getId())) {
			return true;
		} else {
			return false;
		}
	}
}
