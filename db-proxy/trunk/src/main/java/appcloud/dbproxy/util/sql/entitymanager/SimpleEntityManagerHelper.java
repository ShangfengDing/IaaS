/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */
package appcloud.dbproxy.util.sql.entitymanager;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import appcloud.dbproxy.util.sql.IEntityManagerHelper;

/**
 * 简单的JPA数据库管理器
 * （可能会存在长链接问题，对于定期执行的数据库程序较为适用）
 * @author yicou
 * 
 * Moved by weed
 */
public class SimpleEntityManagerHelper implements IEntityManagerHelper {
	private static Logger logger = Logger.getLogger(SimpleEntityManagerHelper.class);
    private static final Map<String, EntityManagerFactory> threadLocalMap =
            new HashMap<String, EntityManagerFactory>();

    public EntityManager getEntityManager(String PUName) {
        EntityManagerFactory emf;
        if (!threadLocalMap.containsKey(PUName)) {
        	synchronized (threadLocalMap) {
				if(!threadLocalMap.containsKey(PUName)) {
					emf = Persistence.createEntityManagerFactory(PUName);
		            threadLocalMap.put(PUName, emf);
				} else {
					logger.info("减少不必要的连接池创建");
				}
			}
        } 
        emf = threadLocalMap.get(PUName);

        EntityManager manager = emf.createEntityManager();
        return manager;
    }
}
