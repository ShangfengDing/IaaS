package com.distributed.common.service.db;


import com.distributed.common.entity.VmBack;

import java.util.List;

/**
 *
 * @author Idan
 * @date 2017/12/12
 */
public interface VmBackupService {

    VmBack findByUuid(String uuid);

    // 根据参数动态查询备份的信息
    List<VmBack> findByParams(String instanceUuid, String sourceHostUuid, String destHostUuid, String sourceDataCenter, String destDataCenter, String status, Boolean isDetailForInfo);

    List<VmBack> findBySrcDataCenter(String srcDataCenter);

    List<VmBack> findByDestDataCenter(String destDataCenter);

    Boolean updateVmBack(String vmBackUuid, String sourceHostUuid, String destHostUuid, String sourceDataCenter, String destDataCenter, String status);

    void deleteVmBack(Integer id);

    Boolean add(VmBack vmBack);

}
