package appcloud.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * nginx外部IP和内部IP，对应的域名后缀
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Nginx {
	private Integer id;
	private String externalIp;	//对外的ip地址
	private String innerIp;		//对内的ip地址
	private List<DomainSuffix> domainSuffixs;//对应的域名后缀
	private String extend;		//扩展字段
	
	public Nginx() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Nginx(Integer id, String externalIp, String innerIp,
			List<DomainSuffix> domainSuffixs, String extend) {
		super();
		this.id = id;
		this.externalIp = externalIp;
		this.innerIp = innerIp;
		this.domainSuffixs = domainSuffixs;
		this.extend = extend;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExternalIp() {
		return externalIp;
	}
	public void setExternalIp(String externalIp) {
		this.externalIp = externalIp;
	}
	public String getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	public List<DomainSuffix> getDomainSuffixs() {
		return domainSuffixs;
	}
	public void setDomainSuffixs(List<DomainSuffix> domainSuffixs) {
		this.domainSuffixs = domainSuffixs;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	@Override
	public String toString() {
		return "Nginx [id=" + id + ", externalIp=" + externalIp + ", innerIp="
				+ innerIp + ", domainSuffixs=" + domainSuffixs + ", extend="
				+ extend + "]";
	}
	
}
