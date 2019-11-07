package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.VolumeAttachment;
import appcloud.api.checkutils.VolumeChecker;
import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.manager.VolumeAttachmentManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.rpc.tools.RpcException;

public class RealVolumeAttachmentManager implements VolumeAttachmentManager {
	private static Logger logger = Logger.getLogger(RealVolumeAttachmentManager.class);
	
	private VmVolumeProxy volumeProxy;
	private BeansGenerator generator;
	private ResourceSchedulerService scheduler;
	
	private static RealVolumeAttachmentManager manager = new RealVolumeAttachmentManager();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealVolumeAttachmentManager.class);
			
	public static RealVolumeAttachmentManager getInstance() {
		return manager;
	}
	
	private RealVolumeAttachmentManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public List<VolumeAttachment> getList(String tenantId, String serverId,
			boolean detailed) throws Exception {
		logger.info(String.format("User %s request to get VOLUMEATTACHMENTS", tenantId));
		List<VolumeAttachment> attachments = new ArrayList<VolumeAttachment> ();
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", serverId, FilterBeanType.EQUAL));
		
		List<? extends VmVolume> vmVolumes = volumeProxy.searchAll(query, false, false, false, false);
		for(VmVolume vmVolume : vmVolumes) {
			attachments.add(generator.volumeToVolumeAttachment(vmVolume, detailed));
		}
		return attachments;
	}

	@Override
	public VolumeAttachment get(String tenantId, String serverId,
			String attachmentId) throws Exception {
		logger.info(String.format("User %s request to GET VOLUMEATTACHMENT %s", tenantId, attachmentId));
		//InstanceChecker.checkOwner(tenantId, serverId);
		VmVolume vmVolume = VolumeChecker.checkAttach(tenantId, serverId, attachmentId);
		
		return generator.volumeToVolumeAttachment(vmVolume, true);
	}

	@Override
	public VolumeAttachment attach(String tenantId, String serverId,
			VolumeAttachment attachment) throws Exception {
		logger.info(String.format("User %s request to ATTACH VOLUMEATTACHMENT %s to server %s", tenantId, attachment.volumeId, serverId));
	
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.ATTACH_VOLUME, String.format("User %s request to ATTACH VOLUMEATTACHMENT %s to server %s",
				tenantId, attachment.volumeId, serverId),
				rpcExtra);
		try {
			VolumeChecker.checkOwner(tenantId, attachment.volumeId);	
			VolumeChecker.checkInstanceStatus(tenantId, serverId, VolumeOperationEnum.ATTACH);
		}catch (Exception e) {
			loller.warn(LolLogUtil.ATTACH_VOLUME, "attach volume:"+e.getMessage(), rpcExtra);
			throw e;
		}
		String mountPoint = null;
		try {
			mountPoint = scheduler.attachVolume(attachment.volumeId, serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.ATTACH_VOLUME, rpcExtra);
			return new VolumeAttachment();
		}
		logger.info(String.format("VOLUMEATTACHMENT %s attached to %s successfully", attachment.volumeId, mountPoint));
		//FIXME
		return attachment;
	}

	@Override
	public void detach(String tenantId, String serverId, String attachmentId)
			throws Exception {
		logger.info(String.format("User %s request to DETACH VOLUMEATTACHMENT %s", tenantId, attachmentId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.DETACH_VOLUME, String.format("User %s request to DETACH VOLUMEATTACHMENT %s", tenantId, attachmentId),
				rpcExtra);
		try {
			VolumeChecker.checkAttach(tenantId, serverId, attachmentId);
			VolumeChecker.checkInstanceStatus(tenantId, serverId, VolumeOperationEnum.DETTACH);
		}catch (Exception e) {
			loller.warn(LolLogUtil.DETACH_VOLUME, "detach volume:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.detachVolume(attachmentId, serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DETACH_VOLUME, rpcExtra);
			return;
		}
		logger.info(String.format("VOLUMEATTACHMENT %s detached successfully", attachmentId));
	}
}
