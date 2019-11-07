package com.distributed.common.service.db;

import com.distributed.common.entity.VmHost;

import java.util.List;

/**
 * Created by Idan on 2018/3/13.
 */
public interface VmHostService extends BasicService<VmHost,Integer>{

    VmHost findByUuid(String uuid);

    List<VmHost> findByParams(String hostUuid, String type, String ip, Integer availabilityZoneID, Integer clusterID);

}