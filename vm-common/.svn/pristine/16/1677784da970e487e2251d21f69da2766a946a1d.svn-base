package appcloud.common.transaction;

import org.apache.log4j.Logger;

public class DefaultTTask extends TTask {
	private static final Logger logger = Logger.getLogger(DefaultTTask.class);
	@Override
	public boolean preProcess() throws Exception {
		logger.debug("preProcess");
		return true;
	}

	@Override
	public void process() throws Exception {
		logger.debug("process");
	}

	@Override
	public void postProcess() throws Exception {
		logger.debug("postProcess");
	}

	@Override
	public void onError() {
		logger.debug("onError");
	}

	@Override
	public void onCompleted() {
		logger.debug("onCompleted");
	}

}
