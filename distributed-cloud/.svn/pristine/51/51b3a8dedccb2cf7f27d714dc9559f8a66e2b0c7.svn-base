package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmZoneEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Idan on 2018/3/13.
 */
@Transactional
public interface VmZoneDao extends CrudRepository<VmZoneEntity, Integer> {

    VmZoneEntity findByZoneId(String zoneId);

}
