package com.distributed.common.service.controller;

import com.distributed.common.entity.CloudInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zouji on 2018/1/9.
 */
public interface RouteInfoService {

    Set<String> findRegionIds();
    List<Map<String, String>> findProductDomains();
    Map<String, String> findOwnRouteInfo();
    List<CloudInfo> findAllCloudInfo();
    CloudInfo findMonitorCloudInfo();
}
