package appcloud.openapi.operate;

import appcloud.api.beans.SecurityGroup;
import appcloud.openapi.response.SecurityGroupRulesReponse;
import appcloud.openapi.response.SecurityGroupsDetailReponse;

public interface SecurityGroupOperate {

	
	/**
	 * 创建安全组
	 * @param appkeyId
	 * @param groupId
	 * @param name
	 * @param description
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public String create (String appkeyId, String groupId, String name,String description, String requestId) throws Exception;
	
	/**
	 * 删除安全组
	 * @param appkeyId
	 * @param securityGroupId
	 * @param diskId
	 * @return
	 */
	public Boolean delete (String appkeyId, String securityGroupId, String requestId) throws Exception;
	
	
	/**
	 * 删除安全组
	 * @param appkeyId
	 * @param securityGroupId
	 * @param diskId
	 * @return
	 */
	public Boolean createRule (String appkeyId, String securityGroupId, String ipProtocol,
			String portRange, String sourceCidrIp, String policy, String requestId) throws Exception;
	
	
	/**
	 * 查询安全组列表
	 * @param appkeyId
	 * @param RegionId
	 * @param pageNum
	 * @param pageSize
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public SecurityGroupsDetailReponse describe (String appkeyId, String RegionId, String pageNum, String pageSize, String requestId) throws Exception;
	
	/**
	 * 删除安全规则
	 * @param appkeyId
	 * @param ruleIds
	 * @param requestId
	 * @return
	 */
	public Boolean deleteRules (String appkeyId, String ruleIds, String requestId) throws Exception;
	
	/**
	 * 查询安全组规则
	 * @param appkeyId
	 * @param securityGroupId
	 * @param diskId
	 * @return
	 */
	public SecurityGroupRulesReponse describeSGAttitude (String appkeyId, String securityGroupId, String requestId) throws Exception;
	
	/**
	 * 修改安全组属性
	 * @param appkeyId
	 * @param securityGroupId
	 * @param SecurityGroupName
	 * @param description
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public Boolean modifyAttitude (String appkeyId, String securityGroupId, String SecurityGroupName, String description, String requestId) throws Exception;
}
