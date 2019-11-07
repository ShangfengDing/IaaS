package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.SnapshotOperateCheck;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.SnapshotChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

@Component
public class SnapshotOperateCheckImpl implements SnapshotOperateCheck{
	private static Logger logger = Logger.getLogger(SnapshotOperateCheckImpl.class);
	
	private static SnapshotOperateCheckImpl volumeOperateCheck = new SnapshotOperateCheckImpl();
	public static SnapshotOperateCheckImpl getInstance(){
		return volumeOperateCheck;
	}
	
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmUserProxy vmUserProxy;
	
	private SnapshotOperateCheckImpl() {
		super();
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}


	@Override
	public Map<String, String> checkCreate(Map<String, String> paramsMap)
			throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(user.getUserId());
		
		SnapshotChecker.checkCreateSnapshot(userId,
				paramsMap.get(Constants.DISK_ID));

		if (!AcGroupChecker.checkSnapshotCount(userId)) {
			String message = "user "
					+ userId
					+ " request to create snapshot while snapshot number reaches the upper limit";
			logger.warn(message);
			throw new OperationFailedException(
					"user's snapshot number reaches the upper limit");
		}
		
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkDelete(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String snapshotId = paramsMap.get(Constants.SNAPSHOT_ID);
			SnapshotChecker.checkOwner(userId, snapshotId);
		}catch (Exception e) {
			logger.warn("delete snapshot:"+e.getMessage());
			throw e;
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		if (null != paramsMap.get(Constants.SNAPSHOT_IDS)) {
			try {
				JSONArray array = new JSONArray(paramsMap.get(Constants.SNAPSHOT_IDS));
				for (int i = 0; i < array.length(); i++) {
					String snapshotId = (String) array.get(i);
					SnapshotChecker.checkOwner(userId, snapshotId);	
				}
				
			}catch (Exception e) {
				logger.warn("attach snapshot fialed! operation is not allowed:"+e.getMessage());
				return commonGenerator.operationDeny("attach snapshot fialed! operation is not allowed:"+e.getMessage(), null);
			}
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

}
