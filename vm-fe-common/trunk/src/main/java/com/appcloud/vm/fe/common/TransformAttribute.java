package com.appcloud.vm.fe.common;

public class TransformAttribute {
	public TransformAttribute(){};
	/**
	 *	转换云主机状态
	 */
	public String transformStatus(String status) {
		if(null==status) return "任务进行中";
		switch (status) {
		case "active":
			return "运行中";
		case "Running":
			//阿里云主机“运行中的状态表示”
			return "运行中";
		case "running":
			//阿里云主机“运行中的状态表示”
			return "运行中";
		case "stopped":
			 return "已关机";
		case "Stopped":
			 return "已关机";
		case "STOPPED":
			//阿里云主机“已关机的状态表示”
			return "已关机";
		case "building":
			return "创建中";
		case "deleted":
			return "已删除";
		case "error":
			return "故障";
		case "rebuilding":
			return "ISO装机中";
		case "suspended":
			return "已挂起";
		default:
			return "任务进行中";
		}
	}
	/**
	 *	转换云服务提供商
	 */
	public String transformProvider(String provider) {
		if(null==provider) return "";
		switch (provider) {
		case Constants.YUN_HAI:
			return "云海";
		case Constants.ALI_YUN:
			 return "阿里云";
		case Constants.AMAZON:
			return "亚马逊";
		default:
			return "";
		}
	}
	/**
	 *	转换地域信息
	 */
	public String transformRegion(String regionId) {
		if(null==regionId) return null;
		switch (regionId) {
		case "beijing":
			return "北京";
		case "BEIJING":
			return "北京";
		case "cn-qingdao":
			return "华北 1";
		case "cn-beijing":
			return "华北 2";
		case "ap-southeast-1":
			return "新加坡";
		case "cn-shenzhen":
			return "华南 1";
		case "cn-hangzhou":
			return "华东 1";
		case "cn-shanghai":
			return "华东 2";
		case "us-east-1":
			return "美东 1";
		case "cn-hongkong":
			return "香港";
//		case "zpark":
//			return "中关村";
//		case "lab":
//			return "实验室";
		case "us-west-1":
			return "美西 1";
		default:
			return "";
		}
	}
	/**
	 *	转换可用区信息
	 */
	public String transformZone(String zoneId) {
		if(null==zoneId) return null;
		switch (zoneId) {
		case "lab":
			return "北邮";
		case "zpark":
			return "中关村";
		default:
			return zoneId;
		}
	}
	/**
	 *	转换云快照状态
	 */
	public String transformSnapshot(String status) {
		if(null==status) return "任务进行中";
		switch (status) {
		case "defined":
			return "定义中";
		case "available":
			return "完成";
		case "creating":
			//阿里云主机“运行中的状态表示”
			return "创建中";
		case "deleting":
			 return "删除中";
		case "error":
			return "故障";
		case "error_deleting":
			return "删除中故障";
		case "deleted":
			return "已删除";
		case "accomplished":
			return "已完成";
		default:
			return "任务进行中";
		}
	}
}
