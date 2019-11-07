package appcloud.common.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import appcloud.common.model.*;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.tools.RpcException;

public interface LolLogService {
	
	
	/**
	 * @param message
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 100)
	public void add(MessageLog message) 
			throws RpcException;

	/**
	 * 查找messageLog
	 * @param message  if you want to search messages which module="volume-provider", set message.module = "volume-provider"
	 * @param startTime
	 * @param endTime   bigger than starTtime
	 * @param size  bigger than 0, smaller than 200
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	public List<MessageLog> search(MessageLog message, Timestamp startTime, Timestamp endTime, Integer size) 
			throws RpcException;
	
	/**
	 * 查找messageLog
	 * @param message  if you want to search messages which module="volume-provider", set message.module = "volume-provider"
	 * @param startTime
	 * @param endTime   bigger than starTtime
	 * @param size  bigger than 0, smaller than 200
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	public List<MessageLog> searchTimeDesc(MessageLog message, Timestamp startTime, Timestamp endTime, Integer size) 
			throws RpcException;
	
	/***
	 * @param mailConf
	 * 
	 * lolEmail  true：发送邮件  false：不发送邮件
	 * 
	 * emailHost  emailFrom emailPassword emailSubject  如果更新，这四个都要有值
	 * 如果其中一个为“”或者null，则抛异常，如果四个配置导致邮件无法发出，则配置文件自动回滚（下同）
	 *   
	 * Map<String, String> moduleInCharge 用来设置各个模块的负责人，即收件人，多个收件人以逗号分隔
	 * 
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 100)
	public void mailSet(MailConf mailConf)
			throws RpcException;
	
	/**
	 * 获取目前的警告设置的配置，主要包括邮件配置以及各个模块负责人配置
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 20)
	public MailConf currentMailSet()
			throws RpcException;

	/**
	 *
	 * @param operateLogType
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 100)
	void addOperateLog(OperateLogType operateLogType) throws RpcException;

	/**
	 *
	 * @param operateLogType
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 100)
	List<OperateLogType> searchOperateLog(OperateLogType operateLogType,  Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc) throws RpcException;

	/**
	 *
	 * @param instanceLogType
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	void addInstanceLog(InstanceLogType instanceLogType)
			throws RpcException;

	/**
	 *
	 * @param instanceLogType
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	void updateInstanceLog(InstanceLogType instanceLogType)
			throws RpcException;
	/**
	 *
	 * @param instanceLogType
	 * @param startTime
	 * @param endTime
	 * @param size
	 * @param timeasc
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 100)
	List<InstanceLogType> searchInstanceLog(InstanceLogType instanceLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc)
			throws RpcException;
}
