package appcloud.admin.dao;

import appcloud.admin.model.Service;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 *
 *
 * @package com.free4inno.cloudAIMonitor.dao
 * @class ServiceDAO
 * @author Boyang
 * @date 2018/5/13 17:54
 */
public class ServiceDAO extends AbstractDAO{

    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ServiceDAOSingletoHolder {
        static ServiceDAO instance = new ServiceDAO();
    }

    public static ServiceDAO getInstance() {
        return ServiceDAO.ServiceDAOSingletoHolder.instance;
    }

    @Override
    public Class<Service> getEntityClass() {
        return Service.class;
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

    public List<appcloud.admin.model.Service> findAll(Integer page, Integer size, String nameorder, Boolean order){
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
            List<Service> serviceList;
            serviceList = query.getResultList();

            return serviceList;
        } catch (RuntimeException var5) {
            this.log("find all failed", Level.SEVERE, var5);
            throw var5;
        }
    }


}
