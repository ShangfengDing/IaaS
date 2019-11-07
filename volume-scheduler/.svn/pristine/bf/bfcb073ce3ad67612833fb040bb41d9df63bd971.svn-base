package appcloud.vs.strategy.filter;

import appcloud.common.model.Host;

/**
 * the base class of filter,other filter extends this and implements the
 * hostPass function.
 * 
 * @author haowei.yu
 * 
 */
public abstract class BaseHostFilter extends BaseFilter {

	public boolean filterOne(Host host,Integer size, Host exHost) {
		return hostPass(host, size, exHost);
	}

	protected abstract boolean hostPass(Host host, Integer size, Host exHost);

}
