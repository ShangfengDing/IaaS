package com.appcloud.vm.fe.model;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class SnapshotBackupNumDAO extends AbstractDAO<SnapshotBackupNum>{

	public String getClassName(){
		return getEntityClass().getName();
	}
	
	@Override
	public Class<SnapshotBackupNum> getEntityClass() {
		return SnapshotBackupNum.class;
	}
	
	public static final String PU_NAME = "VMC_PU";
	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	/**
	 * 查找系统设置的vm快照、备份个数
	 * @return
	 */
	public SnapshotBackupNum findSnapshotBackupNum(){
		try{
			return findAll().get(0);
		}
		catch(Exception e){
			return null;
		}
	}
}
