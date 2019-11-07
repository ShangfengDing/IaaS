package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class VmUser implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer preId;
    private Integer userId;
    private String userEmail;
    private Boolean active;
    private Integer groupId;
    private String appkeyId;
    private String appkeySecret;
    private Integer enterpriseId;

    public VmUser() {}

    public VmUser(Integer preId, Integer userId, String userEmail, Boolean active, Integer groupId, String appkeyId, String appkeySecret, Integer enterpriseId) {
        this.preId = preId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.active = active;
        this.groupId = groupId;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.enterpriseId = enterpriseId;
    }
}
