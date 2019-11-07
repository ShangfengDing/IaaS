package appcloud.admin.dao;

import appcloud.admin.model.AlarmHistory;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by zouji on 2018/4/30.
 */
public class AlarmHistoryDAO extends AbstractDAO<AlarmHistory> {

    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class AlarmHistoryDAOSingletoHolder {
        static AlarmHistoryDAO instance = new AlarmHistoryDAO();
    }

    public static AlarmHistoryDAO getInstance() {
        return AlarmHistoryDAO.AlarmHistoryDAOSingletoHolder.instance;
    }

    @Override
    public Class<AlarmHistory> getEntityClass() {
        return AlarmHistory.class;
    }

    private static final String PU_NAME = "freelol_PU";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }

    public List<AlarmHistory> search(final List<Object> values, Long startTime, Long endTime, Boolean order, Integer size, Integer page) {
        if (startTime == null && endTime == null) {
            List<AlarmHistory> list=new ArrayList<>();
            Map<String,Object> params = new HashMap<>();
            Map<String,List<Object>> paramslist=new HashMap<>();
            paramslist.put("appName",values);
            list=findByProperty(null,paramslist,null,null,"time",false);
//            for(Object o:values){
//                List<AlarmHistory> listin=findByProperty("appName",o);
//                for(AlarmHistory ah:listin){
//                    list.add(ah);
//                }
//            }
            return list;
        }

        if (startTime != null && endTime != null && startTime > endTime) {
            return new ArrayList<>();
        }

        try {
            String queryString = String.format("select model from %s model where", getClassName());
            if (startTime != null) {
                queryString += " model.time" + ">=" + startTime +" and";
            }
            if (endTime != null) {
                queryString += " model.time" + "<=" + endTime + " and";
            }
            String str = "";
            for (Object value :values) {
                str += "'" + value + "',";
            }
            if (values.size() != 0) {
                str = str.substring(0, str.length() - 1);
                queryString += " model.appName in (" + str + ")";
            }
//            System.out.println(queryString);
            String orderString;
            if (order == null || order){
                orderString = String.format( " ORDER BY time %s","desc");
                queryString += orderString;
            }else {
                orderString = String.format( " ORDER BY time %s","asc");
                queryString += orderString;
            }
            /**
             *  检查附加条件中and是否多余
             */
            if (queryString.endsWith("and")) {
                queryString = queryString.substring(0, queryString.length() - 3);
            }
            Query query = getEntityManager().createQuery(queryString);

            if(page != null && size != null) {
                query.setMaxResults(size.intValue()).setFirstResult(page.intValue() * size.intValue());
            }
            System.out.println("************"+queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            log("find by property name*N failed", Level.SEVERE, re);
            throw re;
        }
    }

    public long countAll(final List<Object> values){

        try {
            String queryString = String.format("select count(model) from %s model where", getClassName());
            String str = "";
            for (Object value :values) {
                str += "'" + value + "',";
            }
            if (values.size() != 0) {
                str = str.substring(0, str.length() - 1);
                queryString += " model.appName in (" + str + ")";
            }
//            System.out.println(queryString);

            /**
             *  检查附加条件中and是否多余
             */
            if (queryString.endsWith("and")) {
                queryString = queryString.substring(0, queryString.length() - 3);
            }
            Query query = getEntityManager().createQuery(queryString);
            System.out.println(queryString);
            Long count = (Long)query.getSingleResult();
            return count.longValue();
        } catch (RuntimeException re) {
            log("find by property name*N failed", Level.SEVERE, re);
            throw re;
        }
    }

    public long count(final List<Object> values,Long startTime,Long endTime){
        if (startTime == null && endTime == null) {
            return countAll(values);
        }

        if (startTime != null && endTime != null && startTime > endTime) {
            return 0;
        }

        try {
            String queryString = String.format("select count(model) from %s model where", getClassName());
            if (startTime != null) {
                queryString += " model.time" + ">=" + startTime +" and";
            }
            if (endTime != null) {
                queryString += " model.time" + "<=" + endTime + " and";
            }
            String str = "";
            for (Object value :values) {
                str += "'" + value + "',";
            }
            if (values.size() != 0) {
                str = str.substring(0, str.length() - 1);
                queryString += " model.appName in (" + str + ")";
            }
//            System.out.println(queryString);

            /**
             *  检查附加条件中and是否多余
             */
            if (queryString.endsWith("and")) {
                queryString = queryString.substring(0, queryString.length() - 3);
            }
            System.out.println(queryString);
            Query query = getEntityManager().createQuery(queryString);
            Long count = (Long)query.getSingleResult();
            return count.longValue();
        } catch (RuntimeException re) {
            log("find by property name*N failed", Level.SEVERE, re);
            throw re;
        }
    }
}
