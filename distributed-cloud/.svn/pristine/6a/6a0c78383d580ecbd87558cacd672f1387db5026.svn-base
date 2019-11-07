package com.distributed.dbproxy.sql.repository.appcloud_front;

import com.distributed.dbproxy.sql.basic.appcloud_front.AppkeyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Idan on 2017/12/21.
 */
@Transactional
public interface AppkeyDao extends CrudRepository<AppkeyEntity, Integer> {

    @Query("FROM AppkeyEntity a where a.zone=:zone AND a.userEmail=:email")
    List<AppkeyEntity> findByZoneAndEmail(@Param("zone") String  zone, @Param("email") String email);

}
