package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * nginx和域名后缀的对应关系
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class NginxToSuffix {

	private Integer id;
	private Integer nginxId;		//nginx的id
	private Integer domainSuffixId;	//域名后缀的id
	
	public NginxToSuffix() {
		super();
	}

	public NginxToSuffix(Integer id, Integer nginxId, Integer domainSuffixId) {
		super();
		this.id = id;
		this.nginxId = nginxId;
		this.domainSuffixId = domainSuffixId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNginxId() {
		return nginxId;
	}

	public void setNginxId(Integer nginxId) {
		this.nginxId = nginxId;
	}

	public Integer getDomainSuffixId() {
		return domainSuffixId;
	}

	public void setDomainSuffixId(Integer domainSuffixId) {
		this.domainSuffixId = domainSuffixId;
	}

	@Override
	public String toString() {
		return "NginxToSuffix [id=" + id + ", nginxId=" + nginxId
				+ ", domainSuffixId=" + domainSuffixId + "]";
	}
	
}
