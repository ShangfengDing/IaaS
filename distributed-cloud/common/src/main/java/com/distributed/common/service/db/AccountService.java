package com.distributed.common.service.db;

import com.distributed.common.entity.Appkey;
import com.distributed.common.entity.VmUser;

/**
 * Created by Idan on 2018/1/15.
 */
public interface AccountService {

    /**
     * 对用户进行操作
     */
    void addUser(VmUser vmUser);

    void modifyUser(VmUser vmUser);

    void deleteUser(VmUser vmUser);

    /**
     * 对appkey的操作
     */
    void addAppkey(Appkey appkey);

    void modifyAppkey(Appkey appkey);

    void deleteAppkey(Appkey appkey);

    Appkey findByZoneIdAndEmail(String zone, String email);

}
