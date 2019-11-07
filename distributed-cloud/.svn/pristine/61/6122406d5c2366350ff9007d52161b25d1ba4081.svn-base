package com.distributed.common.service.db;

import com.distributed.common.entity.VmImageBack;

import java.util.List;

/**
 * Created by Idan on 2017/12/21.
 */
public interface VmImageBackService {

    public List<VmImageBack> findAll();

    public VmImageBack getByUuid(String uuid);

    public VmImageBack getDetailsByUuid(String uuid, boolean withCluster, boolean withZone, boolean withInstance,boolean withHost);

    public VmImageBack getByActiveVolumeUuidAndTop(String activeVolumeUuid, boolean isTop);

    public VmImageBack getByInstanceUuidAndTop(String instanceUuid, boolean isTop);

    public List<VmImageBack> getByInstanceUuid(String instanceUuid);

    public List<VmImageBack> getByActiveVolumeUuid(String activeVolumeUuid);

    public Boolean save(VmImageBack vmImageBack);

    public Boolean update(VmImageBack vmImageBack);

    public Boolean updateTop(String instanceUuid);

    public Boolean deleteByUuid(String uuid);

    public Boolean deleteById(Integer id);


}
