/*
 * $Id: ExampleSupport.java 739661 2009-02-01 00:06:00Z davenewton $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package appcloud.admin.action.base;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.admin.common.Constants;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 自定义的Struts2基类
 * 
 * @author lzc
 * 
 */
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -8486920562321594582L;
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	private Map<String, Object> session = ActionContext.getContext().getSession();

	/**
	 * 取得已登录用户的id
	 */
	
	public String getUserId() {
		return session.get(Constants.ADMIN_ID_KEY).toString();
	}
	/**
	 * 取得登录用户的用户名
	 * 
	 * @return
	 */
	public String getUsername() {
		return (String) session.get(Constants.ADMIN_USERNAME_KEY);
	}
	
	/**
	 * 写lol日志
	 * @param module 模块名称
	 * @param transactionId 事务Id,一条日志id
	 * @param userId 用户Id
	 * @param vmHdUuid 虚拟机或硬盘Id
	 * @param operateDrpt 何种操作
	 * @param logContent 日志内容
	 * @param sourceClass 所在类
	 * @param ipAddress null
	 * @param logLevel 日志级别
	 * @param logTime 写入日志的时间
	 */
	public void addAcMessageLog(AcModuleEnum module, String transactionId,
			String userId, String vmHdUuid, String operateDrpt, String logContent,
			String sourceClass, String ipAddress, AcLogLevelEnum logLevel,
			Date logTime){
		AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
		AcMessageLog log = new AcMessageLog(module, transactionId,
				this.getUsername(), vmHdUuid, operateDrpt, logContent, sourceClass, ipAddress, logLevel,
				logTime);
		AcMessageLog acMessageLog = acMessageLogClient.addLog(log);
		if(acMessageLog == null){
			logger.info("日志写入失败:"+operateDrpt+","+logContent+","+sourceClass+","
					+logLevel+","+logTime);
		}else{
			logger.info("写入日志成功:"+acMessageLog);
		}
	}
}
