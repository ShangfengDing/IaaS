package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmHostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

/**
 * Created by Idan on 2018/3/13.
 */
@Transactional
public interface VmHostDao  extends JpaRepository<VmHostEntity, Integer>,JpaSpecificationExecutor<VmHostEntity> {

    VmHostEntity findByUuid(String uuid);

}
