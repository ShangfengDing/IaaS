package com.appcloud.vm.fe.model;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class BillingrateDAO extends AbstractDAO<Billingrate> {

	@Override
	public Class<Billingrate> getEntityClass() {
		return Billingrate.class;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	public static final String PU_NAME = "VMC_PU";
	@Override
	public String getPUName() {
		return PU_NAME;
	}
	
	/**
     * 三条件查找
     */
    @SuppressWarnings("unchecked")
    public List<Billingrate> findByProperty3(String name1,
            final int value1,String name2,final String value2,String name3,final int value3) {
        log("finding " + getClassName() + " instance with property3: "
                + name1 + ", value1: " + value1 
                + "; propety2: "+ name2 + ", value2: " + value2
                + "; propety3: "+ name3 + ", value3: " + value3, Level.INFO, null);
        String queryString="";
        try {
        	if(value3==-1){
        		queryString = "select model from " + getClassName() + " model where model."
                        + name1 + "= :value1 and model." + name2 + "= :value2";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("value1", value1);
                query.setParameter("value2", value2);
                return query.getResultList();
        	}
        	else{
        		queryString = "select model from " + getClassName() + " model where model."
                        + name1 + "= :value1 and model." + name2 + "= :value2 and model." + name3 + "= :value3";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("value1", value1);
                query.setParameter("value2", value2);
                query.setParameter("value3", value3);
                return query.getResultList();
        	}
            
        } catch (RuntimeException re) {
            log("find by property name3 failed",
                    Level.SEVERE, re);
            throw re;
        }
    }
    
    /**
     * 六条件查找
     */
    /*@SuppressWarnings("unchecked")
    public List<Billingrate> findByProperty6(String name1,
            final int value1,String name2,final int value2,String name3,final int value3,
            String name4,final int value4,String name5,final String value5,String name6,final int value6) {
        log("finding " + getClassName() + " instance with property6: "
                + name1 + ", value1: " + value1 
                + "; propety2: "+ name2 + ", value2: " + value2
                + "; propety3: "+ name3 + ", value3: " + value3
                + "; propety3: "+ name4 + ", value3: " + value4
                + "; propety3: "+ name5 + ", value3: " + value5
                + "; propety3: "+ name6 + ", value3: " + value6,Level.INFO, null);
        String queryString="";
        try {
        	if(value6==-1){
        		queryString = "select model from " + getClassName() + " model where model."
                        + name1 + "= :value1 and model." + name2 + "= :value2 and model." + name3 + "= :value3 and model."
                        +name4+"= :value4 and model."+name5+"= :value5";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("value1", value1);
                query.setParameter("value2", value2);
                query.setParameter("value3", value3);
                query.setParameter("value4", value4);
                query.setParameter("value5", value5);
                return query.getResultList();
        		
        	}
        	else{
        		queryString = "select model from " + getClassName() + " model where model."
                        + name1 + "= :value1 and model." + name2 + "= :value2 and model." + name3 + "= :value3 and model."
                        +name4+"= :value4 and model."+name5+"= :value5 and model."+name6+"= :value6";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("value1", value1);
                query.setParameter("value2", value2);
                query.setParameter("value3", value3);
                query.setParameter("value4", value4);
                query.setParameter("value5", value5);
                query.setParameter("value6", value6);
                return query.getResultList();
        	}
            
        } catch (RuntimeException re) {
            log("find by property name6 failed",
                    Level.SEVERE, re);
            throw re;
        }
    }*/
	
}
