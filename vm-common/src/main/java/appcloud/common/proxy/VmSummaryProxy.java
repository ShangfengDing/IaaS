package appcloud.common.proxy;


import appcloud.common.model.VmSummary;

import java.util.List;


/**
 * @author lin
 * 2018/7/20
 *
 */

public interface VmSummaryProxy {

    /**
     *取得所有管理员信息
     *
     */
    public List<? extends VmSummary> findAll() throws Exception;

    /**
     * 分页取得组信息
     * @param page 第几页，0代表不分页
     * @param size 每页大小，0代表不分页
     */
    public List<? extends VmSummary> findAll(Integer page,Integer size) throws Exception;

    /**
     * 通过UUID获取信息
     * @param uuid
     * @return
     */
    public VmSummary findByUuid(String uuid) throws Exception;

    /**
     * 保存一个新设备的信息
     * @param vmSummary
     * @return
     */
    public void save(VmSummary vmSummary) throws Exception;

    /**
     * 通过uuid删除一个设备信息
     * @param uuid
     * @return
     */
    public void deleteByUuid(String uuid) throws Exception;

    /**
     * 通过name删除一个设备信息
     * @param name
     * @return
     */
    public void deleteByName(String name) throws Exception;

    /**
     * 更新一个设备信息
     * @param vmSummary
     * @return
     */
    public void update(VmSummary vmSummary) throws Exception;

    /**
     * 查询总条数
     */
    public long countAll() throws Exception;

    public void deleteById(Integer id) throws Exception;

    public VmSummary findById(Integer id) throws Exception;

    public List<? extends VmSummary> findByType(String type) throws Exception;
}
