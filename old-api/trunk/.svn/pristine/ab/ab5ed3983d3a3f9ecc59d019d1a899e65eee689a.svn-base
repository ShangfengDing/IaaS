package appcloud.api.manager.real;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.AcMessageLogManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.MailConf;
import appcloud.common.model.MessageLog;
import appcloud.common.service.LolLogService;
import appcloud.common.util.ConnectionFactory;

public class RealAcMessageLogManager implements AcMessageLogManager {
	
	private static Logger logger = Logger.getLogger(RealAcHostManager.class);
	private BeansGenerator generator;
	private LolLogService lol;
	
	private static RealAcMessageLogManager manager = new RealAcMessageLogManager();
	
	private RealAcMessageLogManager() {
		super();
		generator = BeansGenerator.getInstance();
		lol = (LolLogService) ConnectionFactory.getAMQPService(LolLogService.class, RoutingKeys.LOL_SERVER);
	}
	
	public static RealAcMessageLogManager getInstance() {
		return manager;
	}
	
	@Override
	public AcMessageLog addLog(String tenantId, AcMessageLog log)
			throws Exception {
		logger.info(String.format("Administrator %s request to add log  %s", tenantId, log));
		MessageLog messageLog = generator.AcMessageLogToMessageLog(log);
		lol.add(messageLog);
		return log;
	}

	@Override
	public List<AcMessageLog> searchLog(String tenantId, AcMessageLog log, 
			Date startDate, Date endDate, int count, int order)throws Exception {
		logger.info(String.format("Administrator %s request to search log, %s, " +
				"startDate:%s, endDate:%s, count:%s", tenantId, log, startDate, endDate, count));
		MessageLog message = generator.AcMessageLogToMessageLog(log);
		Timestamp tsStart = null;
		if(startDate != null)
			tsStart = new Timestamp(startDate.getTime());
		Timestamp tsEnd = null;
		if(endDate != null)
			tsEnd = new Timestamp(endDate.getTime());
		List <MessageLog> messageLogs = null;
		if(order == 0) {
			messageLogs = lol.search(message, tsStart, tsEnd, count);
		} else {
			messageLogs = lol.searchTimeDesc(message, tsStart, tsEnd, count);
		}

		List<AcMessageLog> acLogs = new ArrayList<AcMessageLog>();
		for(MessageLog messageLog: messageLogs) {
			acLogs.add(generator.MessageLogToAcMessageLog(messageLog));
		}
		return acLogs;
	}

	@Override
	public AcMailConf getMailConf(String tenantId) throws Exception {
		logger.info(String.format("Administrator %s request to get mailConfigure", tenantId));
		MailConf mailConf = lol.currentMailSet();
		return generator.mailConfToAcMailConf(mailConf);
	}

	@Override
	public AcMailConf mailSet(String tenantId, AcMailConf acMailConf) throws Exception {
		logger.info(String.format("Administrator %s request to configure mail set: %s", tenantId, acMailConf));
		try {
			lol.mailSet(generator.AcMailConfToMailConf(acMailConf));
		}catch (Exception e) {
			logger.info("configure failed");
			throw new OperationFailedException("configure mail failed");
		}
		return acMailConf;
	}

}
