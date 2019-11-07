package com.distributed.common.service.db;

import com.distributed.common.entity.VmInstance;

/**
 * Created by Idan on 2018/3/12.
 */
public interface VmInstanceService {

    VmInstance findByUuid(String uuid, Boolean isDetail);

}
