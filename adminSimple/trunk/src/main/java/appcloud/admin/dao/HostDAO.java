package appcloud.admin.dao;

import appcloud.admin.model.Host;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Boyang on 2018/4/11.
 * @author BZbyr
 */
public class HostDAO extends AbstractDAO<Host> {

    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class HostDAOSingletoHolder {
        static HostDAO instance = new HostDAO();
    }

    public static HostDAO getInstance() {
        return HostDAOSingletoHolder.instance;
    }

    @Override
    public Class<Host> getEntityClass() {
        return Host.class;
    }

    private static final String PU_NAME = "iaas_ai_monitor_PU";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }



    /**
     *
     * @param propertyName 搜索所需属性的名称
     * @param value 搜索所需属性的值
     * @param startTime 搜索开始时间，格式Timestamp
     * @param endTime 搜索结束时间，格式Timestamp
     * @param order Boolean true: 降序；false：升序
     * @param size Int 大小
     * @return java.util.List<com.free4inno.cloudAIMonitor.model.Host>
     */
    public List search(String propertyName, final Object value, Timestamp startTime, Timestamp endTime, Boolean order,Integer size) {
        if (propertyName == null && startTime == null && endTime == null) {
            return findAll();
        }

        if (startTime != null && endTime != null && startTime.after(endTime)) {
            return new ArrayList<>();
        }

        try {
            String queryString = String.format("select model from %s model where", getClassName());
            if (startTime != null) {
                queryString += " model.createdTime" + ">='" + startTime +"' and";
            }
            if (endTime != null) {
                queryString += " model.createdTime" + "<='" + endTime + "' and";
            }
            if (propertyName != null) {
                queryString += " model." + propertyName + "='" + value + "'";
            }

            String orderString;
            if (order == null || order){
                orderString = String.format( " ORDER BY created_time %s","desc");
                queryString += orderString;
            }else {
                orderString = String.format( " ORDER BY created_time %s","asc");
                queryString += orderString;
            }
            String sizeString;
            if (size != null){
                sizeString = String.format(" LIMIT %d",size);
                queryString += sizeString;
            }
            /*
             *  检查附加条件中and是否多余
             */
            if (queryString.endsWith("and")) {
                queryString = queryString.substring(0, queryString.length() - 3);
            }
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            log("find by property name*N failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     *
     *
     * @param page the page 分页页数
     * @param size the size 页面大小
     * @param nameorder the nameorder 排序属性
     * @param order the order true：倒序，false：正序
     * @return  java.util.List
     */
    @SuppressWarnings("unchecked")
    public List<Host> findAll(Integer page, Integer size,String nameorder,Boolean order) {
        this.log("finding all " + this.getClassName() + " instances", Level.INFO, (Throwable)null);

        try {
            String queryString = "select model from " + this.getClassName() + " model";

            if (nameorder != null) {
                queryString = String.format("%s order by model.%s", queryString, nameorder);
                if (order) {
                    queryString = String.format("%s DESC", queryString);
                } else {
                    queryString = String.format("%s ASC", queryString);
                }
            }
            Query query = getEntityManager().createQuery(queryString);
            if (page != null && size != null) {
                query.setMaxResults(size).setFirstResult(page * size);
            }
            List<Host> hostList;
            hostList = query.getResultList();

            return hostList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }
}
