package com.appcloud.vm.fe.model;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.appcloud.vm.fe.model.FilterBean;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmHdEndtimeDAO extends AbstractDAO<VmHdEndtime>{
	
	public String getClassName(){
		return getEntityClass().getName();
	}
	
	@Override
	public Class<VmHdEndtime> getEntityClass() {
		return VmHdEndtime.class;
	}
	
	public static final String PU_NAME = "VMC_PU";
	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	public void deleteByUuidAndType(String uuid, String type) {
		List<VmHdEndtime> deletedList = findByProperty2("uuid", uuid, "type", type);
		for (VmHdEndtime deleted : deletedList) {
			deleteByPrimaryKey(deleted.getId());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<VmHdEndtime> findByUidsAndTypes(List<Integer> uids, List<String> types){
		log("finding " + getClassName() + " instance with property:uid , value: " 
    			+ uids +"types, value:"+types, Level.INFO, null);
    	
    	if(uids.size() == 0 || types.size() == 0){
    		return Collections.emptyList();
    	}

    	String queryString = "select model from " + getClassName() + 
    			" model where ";
    	for (int i = 0; i < uids.size(); i++){
			if(i == 0){
				queryString += "( model.userId = " + uids.get(i);
			}else{
				queryString += " or " + "model.userId = " + uids.get(i);
			}
		}

    	queryString += " ) and ";
    	
    	for (int i = 0; i < types.size(); i++){
			if(i == 0){
				queryString += "( model.type = '" + types.get(i)+"'";
			}else{
				queryString += " or " + "model.type = '" + types.get(i)+"'";
			}
		}
    	
    	queryString += " )";
    	
    	log(queryString, Level.INFO, null);
    	
		try {
    		Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
        } catch (RuntimeException re) {
            log("findByUidsAndTypes failed", Level.SEVERE, re);
            throw re;
        }
	}
	

    @SuppressWarnings("unchecked")
    public List<VmHdEndtime> findByProperties(List<FilterBean> filterBeans) {
        
    	if(filterBeans == null || filterBeans.size() == 0) {
    		log("find by properties, filterBeans is null", Level.INFO, null);
    		return findAll();
    	}
    	String logStr = "finding " + getClassName() + " instance with properties: " + filterBeans;
    	log(logStr, Level.INFO, null);
    	
    	
        try {
        	String queryStr = "select model from " + getClassName() + " model where model."
                + filterBeans.get(0).getName() + filterBeans.get(0).getType().constrain("value0");

	        for(int i = 1; i < filterBeans.size(); i++) {
	        	queryStr += " and model."
	                    + filterBeans.get(i).getName() + filterBeans.get(i).getType().constrain("value" + i);
	        } 
	        log(queryStr, Level.INFO, null);
	        Query query = getEntityManager().createQuery(queryStr);
	        for(int i = 0; i < filterBeans.size(); i++) {
	        	query.setParameter("value" + i, filterBeans.get(i).getValue());
	        }
	        return query.getResultList();
	    	
        } catch (RuntimeException re) {
            log("find by property name failed",
                    Level.SEVERE, re);
            throw re;
        }
        
    }
}
