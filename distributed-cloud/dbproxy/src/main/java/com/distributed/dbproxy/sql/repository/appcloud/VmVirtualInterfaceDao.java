package com.distributed.dbproxy.sql.repository.appcloud;

import com.distributed.dbproxy.sql.basic.appcloud.VmImageBackEntity;
import com.distributed.dbproxy.sql.basic.appcloud.VmVirtualInterfaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Idan on 2017/12/21.
 */
@Transactional
public interface VmVirtualInterfaceDao extends CrudRepository<VmVirtualInterfaceEntity, Integer> {

    List<VmVirtualInterfaceEntity> findByInstanceUuid(String instanceUuid);

}
