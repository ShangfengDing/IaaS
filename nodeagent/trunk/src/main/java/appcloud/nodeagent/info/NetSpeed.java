package appcloud.nodeagent.info;

/**
 * 网卡读写速率(k/s)
 * @author wylazy
 *
 */
public class NetSpeed {

	private float netInTrafficK;
	private float netOutTrafficK;
	
	public NetSpeed(float netInTrafficK, float netOutTrafficK) {
		this.netInTrafficK = netInTrafficK;
		this.netOutTrafficK = netOutTrafficK;
	}

	/**
	 * 
	 * @return 网卡接收速率(k/s)
	 */
	public float getNetInTrafficK() {
		return netInTrafficK;
	}

	public void setNetInTrafficK(float netInTrafficK) {
		this.netInTrafficK = netInTrafficK;
	}

	/**
	 * 
	 * @return 网卡发送速率(k/s)
	 */
	public float getNetOutTrafficK() {
		return netOutTrafficK;
	}

	public void setNetOutTrafficK(float netOutTrafficK) {
		this.netOutTrafficK = netOutTrafficK;
	}

	@Override
	public String toString() {
		return "NetTraffic [netInTrafficK=" + netInTrafficK
				+ ", netOutTrafficK=" + netOutTrafficK + "]";
	}
	
	
}
