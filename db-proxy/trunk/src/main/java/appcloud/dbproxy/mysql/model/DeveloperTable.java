package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Developer;

/**
 * @author lzc
 *
 */
@Entity
@Table(name="developer")
public class DeveloperTable extends Developer {

	public DeveloperTable() {
		super();
	}

	public DeveloperTable(Developer developer) {
		this.setId(developer.getId());
		this.setEmail(developer.getEmail());
	}

	@Id
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}

	public void setId(Integer id) {
		super.setId(id);
	}

	@Column(name="email")
	public String getEmail() {
		return super.getEmail();
	}

	public void setEmail(String email) {
		super.setEmail(email);
	}
}
