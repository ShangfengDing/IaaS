package com.appcloud.vm.fe.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snapshot_backup_num")
public class SnapshotBackupNum implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer snapshotNum;
	private Integer backupNum;
	
	public SnapshotBackupNum() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "snapshot_num", nullable = false)
	public Integer getSnapshotNum() {
		return snapshotNum;
	}
	public void setSnapshotNum(Integer snapshotNum) {
		this.snapshotNum = snapshotNum;
	}
	
	@Column(name = "backup_num", nullable = false)
	public Integer getBackupNum() {
		return backupNum;
	}
	public void setBackupNum(Integer backupNum) {
		this.backupNum = backupNum;
	}
}
