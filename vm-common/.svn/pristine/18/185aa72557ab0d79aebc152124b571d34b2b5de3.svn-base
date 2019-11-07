package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 资源调度策略
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class ResrcStrategy {
	private Integer id;
	private String name;	//策略名称
	private String algoCode;//算法代号
	private String extend;	//扩展信息
	
	public ResrcStrategy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResrcStrategy(Integer id, String name, String algoCode, String extend) {
		super();
		this.id = id;
		this.name = name;
		this.algoCode = algoCode;
		this.extend = extend;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlgoCode() {
		return algoCode;
	}
	public void setAlgoCode(String algoCode) {
		this.algoCode = algoCode;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	@Override
	public String toString() {
		return "ResrcStrategy [id=" + id + ", name=" + name + ", algoCode="
				+ algoCode + ", extend=" + extend + "]";
	}

}
