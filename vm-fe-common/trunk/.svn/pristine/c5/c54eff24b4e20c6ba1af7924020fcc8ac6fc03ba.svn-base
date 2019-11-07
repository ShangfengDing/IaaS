package com.appcloud.vm.fe.util;

import appcloud.api.client.*;

import com.appcloud.vm.fe.common.Constants;

//单例模式取得各个client
public class ClientFactory {
	private final static String API_SERVER = Constants.API_SERVER;
	
	private static ServerClient serverClient = null;
	private static AcHostClient hostClient = null;
	private static ImageClient	imageClient = null;
	private static FlavorClient flavorClient = null;
	private static SecurityGroupClient securityGroupClient = null;
	private static RuleClient ruleClient = null;
	private static VolumeClient volumeClient = null;
	private static BackupClient backupClient = null;
	private static SnapshotClient snapshotClient = null;
	private static VolumeAttachmentClient volumeAttachmentClient = null;
	private static AcAggregateClient aggregateClient = null;
	private static AddressPoolClient addressPoolClient = null;
	private static AcServiceClient serviceClient = null;
	private static AcVlanClient vlanClient = null;
	private static AcMessageLogClient acMessageLogClient = null;
	private static AcGroupClient acGroupClient = null;
	private static AcUserClient acUserClient = null;
	private static EnterpriseClient enterpriseClient = null;
	private static EnterpriseInvitationClient enterpriseInvitationClient = null;
	private static AcResourceStrategyClient acResourceStrategyClient = null;
	private static KeepAliveClient keepaliveclient = null;
	private static InstanceLogClient instanceLogClient = null;
	private static AcOperateLogClient acOperateLogClient = null;
	
	public ClientFactory() {
	}
	
	public static ServerClient getServerClient() {
		if (serverClient == null) {
			synchronized (ClientFactory.class) {
				if (serverClient == null) {
					serverClient = new ServerClient(API_SERVER);
				}
			}
		}
		return serverClient;
	}
	public static AcHostClient getHostClient() {
		if (hostClient == null) {
			synchronized (ClientFactory.class) {
				if (hostClient == null) {
					hostClient = new AcHostClient(API_SERVER);
				}
			}
		}
		return hostClient;
	}
	public static ImageClient getImageClient() {
		if (imageClient == null) {
			synchronized (ClientFactory.class) {
				if (imageClient == null) {
					imageClient = new ImageClient(API_SERVER);
				}
			}
		}
		return imageClient;
	}
	public static FlavorClient getFlavorClient() {
		if (flavorClient == null) {
			synchronized (ClientFactory.class) {
				if (flavorClient == null) {
					flavorClient = new FlavorClient(API_SERVER);
				}
			}
		}
		return flavorClient;
	}
	public static SecurityGroupClient getSecurityGroupClient() {
		if (securityGroupClient == null) {
			synchronized (ClientFactory.class) {
				if (securityGroupClient == null) {
					securityGroupClient = new SecurityGroupClient(API_SERVER);
				}
			}
		}
		return securityGroupClient;
	}
	public static RuleClient getRuleClient() {
		if (ruleClient == null) {
			synchronized (ClientFactory.class) {
				if (ruleClient == null) {
					ruleClient = new RuleClient(API_SERVER);
				}
			}
		}
		return ruleClient;
	}
	public static VolumeClient getVolumeClient() {
		if (volumeClient == null) {
			synchronized (ClientFactory.class) {
				if (volumeClient == null) {
					volumeClient = new VolumeClient(API_SERVER);
				}
			}
		}
		return volumeClient;
	}
	public static BackupClient getBackupClient() {
		if (backupClient == null) {
			synchronized (ClientFactory.class) {
				if (backupClient == null) {
					backupClient = new BackupClient(API_SERVER);
				}
			}
		}
		return backupClient;
	}
	
	public static SnapshotClient getSnapshotClient() {
		if (snapshotClient == null) {
			synchronized (ClientFactory.class) {
				if (snapshotClient == null) {
					snapshotClient = new SnapshotClient(API_SERVER);
				}
			}
		}
		return snapshotClient;
	}
	public static VolumeAttachmentClient getVolumeAttachmentClient() {
		if (volumeAttachmentClient == null) {
			synchronized (ClientFactory.class) {
				if (volumeAttachmentClient == null) {
					volumeAttachmentClient = new VolumeAttachmentClient(API_SERVER);
				}
			}
		}
		return volumeAttachmentClient;
	}
	public static AcAggregateClient getAggregateClient() {
		if (aggregateClient == null) {
			synchronized (ClientFactory.class) {
				if (aggregateClient == null) {
					aggregateClient = new AcAggregateClient(API_SERVER);
				}
			}
		}
		return aggregateClient;
	}
	public static AddressPoolClient getAddressPoolClient() {
		if (addressPoolClient == null) {
			synchronized (ClientFactory.class) {
				if (addressPoolClient == null) {
					addressPoolClient = new AddressPoolClient(API_SERVER);
				}
			}
		}
		return addressPoolClient;
	}
	public static AcServiceClient getServiceClient() {
		if (serviceClient == null) {
			synchronized (ClientFactory.class) {
				if (serviceClient == null) {
					serviceClient = new AcServiceClient(API_SERVER);
				}
			}
		}
		return serviceClient;
	}
	public static AcVlanClient getVlanClient() {
		if (vlanClient == null) {
			synchronized (ClientFactory.class) {
				if (vlanClient == null) {
					vlanClient = new AcVlanClient(API_SERVER);
				}
			}
		}
		return vlanClient;
	}
	public static AcMessageLogClient getAcMessageLogClient() {
		if (acMessageLogClient == null) {
			synchronized (ClientFactory.class) {
				if (acMessageLogClient == null) {
					acMessageLogClient = new AcMessageLogClient(API_SERVER);
				}
			}
		}
		return acMessageLogClient;
	}
	public static AcUserClient getAcUserClient() {
		if (acUserClient == null) {
			synchronized (ClientFactory.class) {
				if (acUserClient == null) {
					acUserClient = new AcUserClient(API_SERVER);
				}
			}
		}
		return acUserClient;
	}
	public static AcGroupClient getAcGroupClient() {
		if (acGroupClient == null) {
			synchronized (ClientFactory.class) {
				if (acGroupClient == null) {
					acGroupClient = new AcGroupClient(API_SERVER);
				}
			}
		}
		return acGroupClient;
	}
	public static EnterpriseClient getEnterpriseClient() {
		if(enterpriseClient == null) {
			synchronized (ClientFactory.class) {
				if(enterpriseClient == null) {
					enterpriseClient = new EnterpriseClient(API_SERVER);
				}
			}
		}
		return enterpriseClient;
	}
	public static EnterpriseInvitationClient getEnterpriseInvitationClient() {
		if(enterpriseInvitationClient == null) {
			synchronized (ClientFactory.class) {
				if(enterpriseInvitationClient == null) {
					enterpriseInvitationClient = new EnterpriseInvitationClient(API_SERVER);
				}
			}
		}
		return enterpriseInvitationClient;
	}
	public static AcResourceStrategyClient getAcResourceStrategyClient() {
		if(acResourceStrategyClient == null){
			synchronized (ClientFactory.class) {
				if(acResourceStrategyClient == null) {
					acResourceStrategyClient = new AcResourceStrategyClient(API_SERVER);
				}
			}
		}
		return acResourceStrategyClient;
	}
	
	public static KeepAliveClient getKeepAliveClient() {
		if (keepaliveclient == null) {
			synchronized (ClientFactory.class) {
				if (keepaliveclient == null) {
					keepaliveclient = new KeepAliveClient(API_SERVER);
				}
			}
		}
		return keepaliveclient;
	}

	public static InstanceLogClient getInstanceLogClient() {
		if (instanceLogClient == null) {
			synchronized (ClientFactory.class) {
				if (instanceLogClient == null) {
					instanceLogClient = new InstanceLogClient(API_SERVER);
				}
			}
		}
		return instanceLogClient;
	}

	public static AcOperateLogClient getAcOperateLogClient() {
		if (acOperateLogClient == null) {
			synchronized (ClientFactory.class) {
				if (acOperateLogClient == null) {
					acOperateLogClient = new AcOperateLogClient(API_SERVER);
				}
			}
		}
		return acOperateLogClient;
	}
}
