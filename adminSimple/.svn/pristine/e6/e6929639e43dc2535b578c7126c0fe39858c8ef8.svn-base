package appcloud.admin.dao;

import appcloud.admin.model.HostPrediction;
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
public class HostPredictionDAO extends AbstractDAO {
    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class HostPredictionDAOSingletoHolder {
        static HostPredictionDAO instance = new HostPredictionDAO();
    }

    public static HostPredictionDAO getInstance() {
        return HostPredictionDAO.HostPredictionDAOSingletoHolder.instance;
    }

    @Override
    public Class<HostPrediction> getEntityClass() {
        return HostPrediction.class;
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
     * @param nameorder the nameorder 排序属性
     * @param order the order true：倒序，false：正序
     * @return  java.util.List
     */
    @SuppressWarnings("unchecked")
    public List<HostPrediction> findAll(String nameorder, Boolean order) {
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
            List<HostPrediction> hostPredictionList;
            hostPredictionList = query.getResultList();

            return hostPredictionList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }

    public List<HostPrediction> findAll(String propertyName, final Object value, String nameorder, Boolean order) {
        this.log("finding all " + this.getClassName() + " instances", Level.INFO, (Throwable)null);

        try {
            String queryString = "select model from " + this.getClassName() + " model" +" where";

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
            List<HostPrediction> hostPredictionList;
            hostPredictionList = query.getResultList();

            return hostPredictionList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }

}
