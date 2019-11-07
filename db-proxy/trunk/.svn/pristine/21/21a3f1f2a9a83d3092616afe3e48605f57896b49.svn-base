package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.ResrcStrategy;

/**
 * @author lzc
 *
 */
@Entity
@Table(name="resrc_strategy")
public class ResrcStrategyTable extends ResrcStrategy {

	public ResrcStrategyTable() {
		super();
	}

	public ResrcStrategyTable(ResrcStrategy resrcStrategy) {
		this.setId(resrcStrategy.getId());
		this.setName(resrcStrategy.getName());
		this.setAlgoCode(resrcStrategy.getAlgoCode());
		this.setExtend(resrcStrategy.getExtend());
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}
	
    @Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	
	@Column(name="algo_code")
	public String getAlgoCode() {
		return super.getAlgoCode();
	}
	public void setAlgoCode(String algoCode) {
		super.setAlgoCode(algoCode);
	}
	
	@Column(name="extend")
	public String getExtend() {
		return super.getExtend();
	}
	public void setExtend(String extend) {
		super.setExtend(extend);
	}
}
