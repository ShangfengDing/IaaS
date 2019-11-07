package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmInstanceEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Idan on 2018/3/12.
 */
@Transactional
public interface VmInstanceDao extends CrudRepository<VmInstanceEntity, Integer> {

    VmInstanceEntity findByUuid(String uuid);

}
