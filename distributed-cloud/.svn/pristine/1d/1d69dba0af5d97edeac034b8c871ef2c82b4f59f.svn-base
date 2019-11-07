package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmImageBackEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Idan on 2017/12/21.
 */
@Transactional
public interface VmImageBackDao extends CrudRepository<VmImageBackEntity, Integer> {

    @Query("FROM VmImageBackEntity v where v.volumeUuid=:uuid")
    VmImageBackEntity findByUuid(@Param("uuid") String uuid);

    List<VmImageBackEntity> findByInstanceUuid(String instanceUuid);

    @Query("FROM VmImageBackEntity v where v.instanceUuid=:instanceUuid AND v.top=:top ORDER BY v.id DESC")
    List<VmImageBackEntity> findByInstanceUuidAndTop(@Param("instanceUuid") String instanceUuid, @Param("top") Boolean top);

    @Modifying
    @Query("UPDATE VmImageBackEntity v SET v.top=FALSE WHERE v.instanceUuid=:instanceUuid")
    void updateTopByInstanceUuid(@Param("instanceUuid") String instanceUuid);

}
