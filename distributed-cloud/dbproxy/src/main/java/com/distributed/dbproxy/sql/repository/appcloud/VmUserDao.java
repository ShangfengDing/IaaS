package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmImageBackEntity;
import com.distributed.dbproxy.sql.basic.appcloud.VmUserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Idan on 2017/12/21.
 */
@Transactional
public interface VmUserDao extends CrudRepository<VmUserEntity, Integer> {
}
