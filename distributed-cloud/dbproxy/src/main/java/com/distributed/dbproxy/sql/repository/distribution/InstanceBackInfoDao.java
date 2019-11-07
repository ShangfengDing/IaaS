package com.distributed.dbproxy.sql.repository.distribution;

import com.distributed.dbproxy.sql.basic.distribution.InstanceBackInfoEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Idan on 2017/12/7.
 */
@Transactional
public interface InstanceBackInfoDao extends CrudRepository<InstanceBackInfoEntity, Integer> {

    InstanceBackInfoEntity findByBackupUuid(String uuid);

}
