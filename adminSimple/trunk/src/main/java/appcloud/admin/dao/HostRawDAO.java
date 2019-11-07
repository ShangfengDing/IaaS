package appcloud.admin.dao;

import appcloud.admin.model.HostRaw;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 *
 *
 * @author Boyang
 * @date 2018/5/01 18:53
 */
public class HostRawDAO extends AbstractDAO {
    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class HostRawDAOSingletoHolder {
        static HostRawDAO instance = new HostRawDAO();
    }

    public static HostRawDAO getInstance() {
        return HostRawDAO.HostRawDAOSingletoHolder.instance;
    }

    @Override
    public Class<HostRaw> getEntityClass() {
        return HostRaw.class;
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
     *
     * @param page the page 分页页数
     * @param size the size 页面大小
     * @param nameorder the nameorder 排序属性
     * @param order the order true：倒序，false：正序
     * @return  java.util.List
     */
    @SuppressWarnings("unchecked")
    public List<HostRaw> findAll(Integer page, Integer size, String nameorder, Boolean order) {
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
            List<HostRaw> hostRawList;
            hostRawList = query.getResultList();

            return hostRawList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }

    public List<HostRaw> findAll(String propertyName, final Object value,Integer page, Integer size, String nameorder, Boolean order) {
        this.log("finding all " + this.getClassName() + " instances", Level.INFO, (Throwable)null);

        try {
            String queryString = "select model from " + this.getClassName() + " model"+" where";

            if (propertyName != null) {
                queryString += " model." + propertyName + "='" + value + "'";
            }
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
            List<HostRaw> hostRawList;
            hostRawList = query.getResultList();

            return hostRawList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }

}