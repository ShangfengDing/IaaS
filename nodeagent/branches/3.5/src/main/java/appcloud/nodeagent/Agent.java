/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */

package appcloud.nodeagent;

import java.sql.Timestamp;
import java.io.IOException;

import org.apache.log4j.Logger;

import appcloud.nodeagent.adaptor.Adaptor;
import appcloud.nodeagent.impl.NodeAgentImpl;
import appcloud.nodeagent.info.NodeAgent;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.model.Host;
import appcloud.common.model.Instance;
import appcloud.common.model.Load;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Instance.InstanceStatusEnum;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.InstanceProxy;
import appcloud.common.proxy.LoadProxy;
import appcloud.common.service.NodeAgentService;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.common.util.ConnectionFactory;

import appcloud.common.InfoProto;
import appcloud.common.InfoProto.InfoMessage;
import appcloud.common.InfoProto.EntityMessage;
import appcloud.common.InfoProto.InfoMessage.Builder;

import appcloud.rpc.ampq.BasicRPCServer;

import tip.client.ClientFactory;
import tip.client.Request;
import tip.client.Response;
import tip.client.TipClient;
import tip.client.TipEndPoint;
/**
 *
 * @author yicou
 * @author jianglei
 */
public class Agent {

    private static BasicRPCServer server = null;
    
    private static HostProxy hostProxy = ConnectionFactory.getHostProxy();
    private static LoadProxy loadProxy = ConnectionFactory.getLoadProxy();
    private static InstanceProxy instanceProxy = ConnectionFactory.getInstanceProxy();
    
	private static Logger logger = Logger.getLogger(Agent.class);
    
    public static final String INFO_TIP_ID = "info";
    
    private static boolean infoUpdated = false;

	public static void main(String[] args) {
		NodeAgentImpl service = new NodeAgentImpl();
		NodeAgent na = NodeAgent.getInstance();
		
		try {
			server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", RoutingKeyGenerator.getNodeAgentRoutingKey(na.getHostInfoWithUuid()), NodeAgentService.class, service);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("server init error!");
	        System.exit(1);
		}
		
		Thread serviceThread =  new Thread(){
			public void run() {
				try {
					server.mainloop();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("server start error!");
			        System.exit(1);
				}
			}
		};
		
		serviceThread.start();
		
		nodeAgentLoop(na);		
    }
	
	private static void nodeAgentLoop(NodeAgent na) {
		long cnt = 0;
		while (true) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cnt++;

			try {
				// 更新Host数据库
				if (cnt % na.getHostInfoInterval() == 0) {
					saveHostInfo(na);
				}
	
				// 保存即刻信息
				if (cnt % na.getHitInterval() == 0) {
					na.hit();
				}
	
				// 更新gc数据库
				if (cnt % na.getGcInfoInterval() == 0) {
					saveGcInfo(na);
				}
			} catch (Exception e) {
				logger.warn("SHIT HAPPENS: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

    /**
     * 保存 host 信息
     * @param na
     */
    private static void saveHostInfo(NodeAgent na) {
        try {
            Host host;
            host = hostProxy.getByUuid(na.getUuid(), false, false, false);
            
            // 如果Host没有被添加，添加host信息
            if (host == null) {
                // new
                host = new Host();
                host.setUuid(na.getUuid());
                
                // 重填基本信息
                fillHostInfo(host);
                
                hostProxy.save(host);
            } else if (!infoUpdated) {
            	// 程序第一次启动的时候更新基本信息
                
            	fillHostInfo(host);
                
            	hostProxy.update(host);
            	infoUpdated = true;
            }
            
            //保存load信息；
            
            Load load = new Load();
            
            load.setFatherUuid(na.getUuid());
            
            load.setCpuUsePercent(na.getUseCpuLoad());
            load.setMemoryUseMb(na.getUseMem());
            load.setDiskUseMb(na.getUseDisk());
            load.setTime(new Timestamp(System.currentTimeMillis()));
            
            loadProxy.save(load);
            logger.info("HOST LOAD info save:" + load);

            //TODO: 考虑实现以下的
            //host.setHostCPULoad(na.getUseCpuLoad());

            if (host.getId() != null && host.getId() != na.getHostId()) {
                na.setHostId(host.getId());
            }
        } catch (Exception ex) {
            logger.warn("save Host info Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private static void fillHostInfo(Host host) {
    	NodeAgent na = NodeAgent.getInstance();
    	
    	host.setType(na.getType());
        host.setAgentAddr(na.getUrl());
        host.setCpuMhz(na.getCpuHz());
        host.setCpuCore(na.getCpuNum());
        host.setMemoryMb(na.getMaxMem());
        host.setDiskMb(na.getDiskCap());
        host.setCapability(na.getCapability());
        host.setIp(na.getUrl());
        host.setLocation(na.getLocation());
        host.setOs(na.getOs());
        host.setStatus(HostStatusEnum.NORMAL_LOAD);
        host.setScheduleTag("nono");
        //TODO: 带完现在为0
        host.setNetworkMb(0);
    }

    private static void saveGcInfo(NodeAgent na) throws Exception {

    	logger.debug("###Saveing GC info!");

        // J2EE
        if (na.containsJ2ee()) {
            InfoMessage gcInfo = getCurrentInfo(na.getJ2eeEpStr(),
                    InfoProto.InfoType.CURRENT,
                    Adaptor.getJ2eeMask());
            if (gcInfo != null) {
                for (EntityMessage entity : gcInfo.getEntitiesList()) {
                	saveInstanceLoad(entity, InstanceTypeEnum.J2EE);
                }
            }
        }

        // VM
        if (na.containsVm()) {
            InfoMessage gcInfo = getCurrentInfo(na.getVmEpStr(),
                    InfoProto.InfoType.CURRENT,
                    Adaptor.getVmMask());
            if (gcInfo != null) {
                for (EntityMessage entity : gcInfo.getEntitiesList()) {
                    saveInstanceLoad(entity, InstanceTypeEnum.VM);
                }
            }
        }
    }

    private static void saveInstanceLoad(EntityMessage entity, InstanceTypeEnum type) throws Exception {
    	logger.debug("saveGc! " + entity.getUuid() + "\t type " + entity.getEntityType() + "\twith appid " + entity.getAppId());
        Instance instance = instanceProxy.getByUuid(entity.getUuid(), false, false, false);
        
        if (instance == null) {
        	//logger.info("instance with uuid: "+ entity.getUuid() +" instance info not found, hence load info not saved");
        	instance = new Instance();
        	
        	instance.setAppUuid(entity.getAppId());
            instance.setHostUuid(NodeAgent.getInstance().getUuid());
            
            String url = entity.getUrl();
            String ip = "";
            String port = "";
            if (type.equals(InstanceTypeEnum.J2EE)) {
            	String[] confs = url.split(":");
            	ip = confs[0];
            	port = confs[1];
            	// to fix gc -1 port bug
            	if (port.contains("-") || Integer.valueOf(port) < 0) {
            		logger.info("entity info is a tmp entity, not saved");
            		return;
            	}
            } else {
            	ip = url;
            }            
            instance.setServiceIP(ip);
            instance.setServicePort(port);
            instance.setType(type);
            instance.setStatus(InstanceStatusEnum.NORMAL_LOAD);
            instance.setUuid(entity.getUuid());

            instanceProxy.save(instance);
        }
        
        Load load = new Load();
        
        float cpu = entity.getCpu();
        cpu *= 100;
        cpu /= entity.getCpuTime();
        load.setCpuUsePercent(cpu);
        load.setDiskUseMb(entity.getDiskUse());
        load.setMemoryUseMb(entity.getMem());
        load.setFatherUuid(entity.getUuid());
        load.setTime(new Timestamp(System.currentTimeMillis()));
        
        logger.debug("Save Gc info into db\t cpu:" + entity.getCpu()
                + "\tcpuTime:" + entity.getCpuTime()
                + "\tusedisk:" + entity.getDiskUse()
                + "\tmem:" + entity.getMem()
                );
        
        loadProxy.save(load);
        logger.info("INSTANCE LOAD SAVED:" + load);
    }

    private static InfoMessage getCurrentInfo(String epStr,InfoProto.InfoType infoType, int type) {
        ClientFactory factory = ClientFactory.getInstance();
        TipClient tipClient = factory.newClient(new TipEndPoint(epStr));
        tipClient.setTimeout(2000);
        logger.debug("conn to: " + epStr);

        try {
            // 构造消息
            Builder msgb = InfoMessage.newBuilder();
            msgb.setFlag(InfoProto.FlagType.REQUEST);
            msgb.setInfoType(infoType);
            msgb.setReqType(type);

            Request request = new Request(msgb.build().toByteArray());
            Response response = new Response();

            int ret;
            ret = tipClient.invoke(INFO_TIP_ID, request, response);
            if (ret != 0) {
                logger.debug("get gc info Error: ret " + ret);
                return null;
            } else {
                InfoMessage resp = InfoMessage.parseFrom(response.getData());
                for (EntityMessage entity : resp.getEntitiesList()) {
                	logger.debug("eneity:" +
                    	"\tuuid:" + entity.getUuid() +
                		"\tappid:" + entity.getAppId() +
                    	"\tEntityType:" + entity.getEntityType());
                }
                return resp;
            }
        } catch (Exception ex) {
        	logger.warn("get gc info Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            tipClient.close();
        }
    }
}
