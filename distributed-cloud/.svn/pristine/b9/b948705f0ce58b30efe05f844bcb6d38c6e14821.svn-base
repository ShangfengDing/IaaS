package com.distributed.dbproxy.sql.repository.distribution;

import com.distributed.dbproxy.sql.basic.distribution.VmBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Idan on 2017/12/7.
 */
@Transactional
public interface VmBackupDao extends JpaRepository<VmBackEntity, Integer>,JpaSpecificationExecutor<VmBackEntity> {
    VmBackEntity findByUuid(String uuid);

    List<VmBackEntity> findBySourceDataCenter(String sourceDataCenter);

    List<VmBackEntity> findByDestDataCenter(String destDataCenter);

    @Modifying
    @Query("UPDATE VmBackEntity v SET v.status=:status WHERE v.sourceHostUuid=:hostUuid")
    void updateBySourceHost(@Param("status") String status, @Param("hostUuid") String hostUuid);

    @Modifying
    @Query("UPDATE VmBackEntity v SET v.status=:status WHERE v.destHostUuid=:hostUuid")
    void updateByDestHost(@Param("status") String status, @Param("hostUuid") String hostUuid);

    @Modifying
    @Query("UPDATE VmBackEntity v SET v.status=:status WHERE v.sourceDataCenter=:dataCenterUuid")
    void updateBySourceDataCenter(@Param("status") String status, @Param("dataCenterUuid") String dataCenterUuid);

    @Modifying
    @Query("UPDATE VmBackEntity v SET v.status=:status WHERE v.destDataCenter=:dataCenterUuid")
    void updateByDestDataCenter(@Param("status") String status, @Param("dataCenterUuid") String dataCenterUuid);

    @Modifying
    @Query("UPDATE VmBackEntity v SET v.status=:status WHERE v.uuid=:vmBackUuid")
    void updateByUuid(@Param("status") String status, @Param("vmBackUuid") String vmBackUuid);
}
