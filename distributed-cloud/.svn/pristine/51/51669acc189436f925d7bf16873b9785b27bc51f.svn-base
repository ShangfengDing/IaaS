package com.distributed.common.response.util;

import com.distributed.common.entity.VmUser;
import com.distributed.common.response.data.User;
import com.distributed.common.utils.ModelUtil;

/**
 * Created by Idan on 2018/1/22.
 * 数据库数据与response的实体类之间的转化类
 */
public class BeanGenerator {


    public static User VmUserToUser(VmUser vmUser) {
        if (ModelUtil.isEmpty(vmUser)) {
            return null;
        }
        return new User(vmUser.getUserId(), vmUser.getUserEmail());
    }


}
