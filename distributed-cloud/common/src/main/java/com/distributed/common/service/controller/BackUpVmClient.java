package com.distributed.common.service.controller;

import com.distributed.common.response.BaseResponse;

/**
 * Created by lizhenhao on 2018/1/5.
 */
public interface BackUpVmClient {

    /**
     * 备份镜像
     * @param requestId
     * @param sourceDataCenter： 数据中心的zone， 所有的数据中心的regionId都是一样，默认是BEIJING
     * @param instanceId
     * @param diskId
     * @param needToClean
     * @param appkeyId
     * @param appkeySecret
     * @param userEmail
     * @param accountName
     * @return
     */
    public BaseResponse backUpVm(String requestId, String sourceDataCenter, String instanceId, String diskId, boolean needToClean, String appkeyId, String appkeySecret, String userEmail, String accountName, String destDataCenter);
}
