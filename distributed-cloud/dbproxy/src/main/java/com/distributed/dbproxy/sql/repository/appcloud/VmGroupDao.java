package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
/**
 * Created by Idan on 2018/3/14.
 */
@Transactional
public interface VmGroupDao extends JpaRepository<VmGroupEntity, Integer>,JpaSpecificationExecutor<VmGroupEntity> {
}
