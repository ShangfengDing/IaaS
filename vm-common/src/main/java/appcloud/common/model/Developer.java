package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 开发者基本信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Developer {
	private Integer id;			//开发者id
	private String email;		//开发者邮箱
	
	private Integer j2eeInfoNum;//开发者J2EE大应用数
	private Integer j2eeAppNum;	//开发者J2EE应用版本总数
	private Integer j2eeInstanceNum;//开发者J2EE应用实例数
	private Integer vmNum;		//开发者虚拟机数
	
	public Developer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Developer(Integer id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getJ2eeInfoNum() {
		return j2eeInfoNum;
	}
	public void setJ2eeInfoNum(Integer j2eeInfoNum) {
		this.j2eeInfoNum = j2eeInfoNum;
	}
	public Integer getJ2eeAppNum() {
		return j2eeAppNum;
	}
	public void setJ2eeAppNum(Integer j2eeAppNum) {
		this.j2eeAppNum = j2eeAppNum;
	}
	public Integer getJ2eeInstanceNum() {
		return j2eeInstanceNum;
	}
	public void setJ2eeInstanceNum(Integer j2eeInstanceNum) {
		this.j2eeInstanceNum = j2eeInstanceNum;
	}
	public Integer getVmNum() {
		return vmNum;
	}
	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}
	@Override
	public String toString() {
		return "Developer [id=" + id + ", email=" + email + ", j2eeInfoNum="
				+ j2eeInfoNum + ", j2eeAppNum=" + j2eeAppNum
				+ ", j2eeInstanceNum=" + j2eeInstanceNum + ", vmNum=" + vmNum
				+ "]";
	}

}
