package com.appcloud.vm.action.vm;

import com.appcloud.vm.action.BaseAction;
import org.apache.log4j.Logger;

public class PreEditVmAction extends BaseAction {
    /**
     * 修改虚拟机名称、描述
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PreEditVmAction.class);

    private String yunType = "";//修改哪一类，云主机、云硬盘、云镜像还是云快照
    private String appname = "";
    private String id = "";//id
    private String name = "";//旧的name
    private String description = "";//旧的描述
    private String type;//修改类型，是名字还是描述
    private String regionId;
    private String target;

    public String execute() {
        logger.info("主机详情：" + regionId + yunType + id + name + type);
        logger.info(description);
        return SUCCESS;
    }

    public String getYunType() {
        return yunType;
    }

    public void setYunType(String yunType) {
        this.yunType = yunType;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
