package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.ResrcStrategy;
import appcloud.common.model.TaskStrategy;

/**
 * @author lzc
 *
 */
@Entity
@Table(name="task_strategy")
public class TaskStrategyTable extends TaskStrategy {

	public TaskStrategyTable() {
		super();
	}

	public TaskStrategyTable(TaskStrategy taskStrategy) {
		this.setId(taskStrategy.getId());
		this.setName(taskStrategy.getName());
		this.setAlgoCode(taskStrategy.getAlgoCode());
		this.setExtend(taskStrategy.getExtend());
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
