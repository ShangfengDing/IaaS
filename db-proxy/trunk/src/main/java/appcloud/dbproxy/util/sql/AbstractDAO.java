/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */
package appcloud.dbproxy.util.sql;

import appcloud.common.util.query.QueryObject;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 基础DAO，这是一个抽象类，请DAO继承此类
 * 
 * @author yicou
 * 
 *         Moved by weed
 */
public abstract class AbstractDAO<T> {

	/**
	 * 获取实体名称，内部使用
	 * 
	 * @return
	 */
	public String getClassName() {
		return getEntityClass().getName();
	}

	/**
	 * 获取实体类型，需要继承，内部使用
	 * 
	 * @return
	 */
	public abstract Class getEntityClass();

	/**
	 * 获取数据库持久单元名称，需要继承，内部使用
	 * 
	 * @return
	 */
	public abstract String getPUName();

	/**
	 * 获取JPA数据库管理器，需要继承，内部使用
	 * 
	 * @return
	 */
	public abstract IEntityManagerHelper getEntityManagerHelper();

	protected EntityManager getEntityManager() {
		return getEntityManagerHelper().getEntityManager(getPUName());
	}

	protected Logger logger = null;

	protected Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger(getClassName());
		}
		return logger;
	}

	/**
	 * 保存多个数据库实例
	 */
	public void save(Collection<T> el) {

		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
		em.getTransaction().begin();

		getLogger().log(Level.INFO, "saving " + getClassName() + " instance", null);
		try {
			Session session = (Session) em.getDelegate();
			session.setFlushMode(FlushMode.MANUAL);

			for (T entity : el) {
				session.save(entity);
			}
			session.flush();
			session.clear();

			getLogger().log(Level.INFO, "save successful", null);
			em.getTransaction().commit();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "save failed", re);
			em.getTransaction().rollback();
			throw re;
		}

	}

	/**
	 * 保存一个数据库实例
	 */
	public void save(T entity) {

		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
		em.getTransaction().begin();

		getLogger().log(Level.INFO, "saving " + getClassName() + " instance", null);
		try {
			em.persist(entity);
			getLogger().log(Level.INFO, "save successful", null);
			em.getTransaction().commit();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "save failed", re);
			em.getTransaction().rollback();
			throw re;
		}

	}

	/**
	 * 通过主键删除一个数据库实例
	 */
	@SuppressWarnings("unchecked")
	public void deleteByPrimaryKey(Object primaryKey) {

		EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
		em.getTransaction().begin();
		getLogger().log(Level.INFO, "deleting " + getClassName() + " instance", null);
		try {
			Object entity = em.getReference(getEntityClass(), primaryKey);
			em.remove(entity);
			getLogger().log(Level.INFO, "delete successful", null);
			em.getTransaction().commit();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "delete failed", re);
			em.getTransaction().rollback();
			throw re;
		}
	}

    /**
     * 通过属性删除
     * added by lzc
     */
    public void deleteByProperty(String propertyName, final Object value) {
    	EntityManager em = getEntityManager();

        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
            getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!", null);
        }
        em.getTransaction().begin();
        getLogger().log(Level.INFO, "deleting " + getClassName() + " instance with property: "
                + propertyName + ", value: " + value, null);
        try {
            final String queryString = "delete from " + getClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = em.createQuery(queryString);
            query.setParameter("propertyValue", value);
            query.executeUpdate();
            getLogger().log(Level.INFO, "delete by property successful", null);
            em.getTransaction().commit();
        } catch (RuntimeException re) {
            getLogger().log(Level.ERROR, "delete by property name failed", re);
            em.getTransaction().rollback();
            throw re;
        }
    }

	/**
	 * 更新多个数据库实例
	 */
	public void update(Collection<T> el) {

		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
		em.getTransaction().begin();

		getLogger().log(Level.INFO, "updating " + getClassName() + " instance", null);
		try {
			for (T entity : el) {
				em.merge(entity);
			}
			getLogger().log(Level.INFO, "update successful", null);
			em.getTransaction().commit();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "update failed", re);
			em.getTransaction().rollback();
			throw re;
		}
	}

	/**
	 * 更新一个数据库实例
	 */
	public T update(T entity) {

		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
		em.getTransaction().begin();

		getLogger().log(Level.INFO, "updating " + getClassName() + " instance", null);
		try {
			T result = em.merge(entity);
			getLogger().log(Level.INFO, "update successful", null);
			em.getTransaction().commit();
			return result;
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "update failed", re);
			em.getTransaction().rollback();
			throw re;
		}
	}

	/**
	 * 通过主键寻找数据库实例
	 * 
	 * @param pKey 主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findByPrimaryKey(Object pKey) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with primary key: " + pKey,
				null);
		try {
			Object instance = getEntityManager().find(getEntityClass(), pKey);
			return (T) instance;
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find failed", re);
			throw re;
		}
	}

	/**
	 * 通过id寻找实体
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Integer id) {
		return findByPrimaryKey(id);
	}

	/**
	 * 查找所有
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		getLogger().log(Level.DEBUG, "finding all " + getClassName() + " instances", null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find all failed", re);
			throw re;
		}
	}

	/**
	 * 查找所有, 包含分页
	 * 
	 * @param page
	 *            从0开始，page0代表最靠前的数据
	 * @param size
	 * 			    判断一下0代表不分页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, int size) {
		getLogger().log(Level.DEBUG, "finding all " + getClassName() + " instances", null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model";
			Query query = getEntityManager().createQuery(queryString);
			if (size != 0) {
				query.setMaxResults(size).setFirstResult(page * size);
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find all failed", re);
			throw re;
		}
	}

	/**
	 * 通过一个属性查找
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, final Object value) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property: "
				+ propertyName + ", value: " + value, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 通过一个属性查找，包含分页
	 * 
	 * @param page
	 *            从0开始，page0代表最靠前的数据
	 * @param size
	 * 			    判断一下0代表不分页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, final Object value,
			int page, int size) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property: "
				+ propertyName + ", value: " + value, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (size != 0) {
				query.setMaxResults(size).setFirstResult(page * size);
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 通过两个属性查找
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty2(String name1, final Object value1,
			String name2, final Object value2) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property1: " + name1
				+ ", value1: " + value1 + "; propety2: " + name2 + ", value2: "
				+ value2, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model." + name1 + "= :value1 and model."
					+ name2 + "= :value2";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value1", value1);
			query.setParameter("value2", value2);
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name2 failed", re);
			throw re;
		}
	}
	
	/**
	 * 通过两个属性查找，包含分页
	 * 
	 * @param page
	 * 			    从0开始，page0代表最靠前的数据
	 * @param size	每页大小
	 * 			    判断一下0代表不分页
	 * @return	返回找到的结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty2(String name1, final Object value1,
			String name2, final Object value2, int page, int size) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property1: " + name1
				+ ", value1: " + value1 + "; propety2: " + name2 + ", value2: "
				+ value2, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model." + name1 + "= :value1 and model."
					+ name2 + "= :value2";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value1", value1);
			query.setParameter("value2", value2);
			if (size != 0) {
				query.setMaxResults(size).setFirstResult(page * size);
			}

			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name2 failed", re);
			throw re;
		}
	}

	/**
	 * 通过多个属性查找，包含分页
	 * @param queryObject	查询条件
	 * @param page	第几页
	 * @param size	每页大小，0标识不分页，查找所有
	 * @return	返回找到的结果
	 * @author weed
	 */
	public List<T> findByProperties(QueryObject queryObject, int page, int size) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with properties: "
				+ queryObject, null);
		try {
			
			StringBuffer sb = new StringBuffer();
			sb.append("select model from " + getClassName() + " model");

			sb.append(queryObject.generateQueryString(new JPAInterpreter()));
			
			getLogger().log(Level.DEBUG, "finding " + getClassName() + " createQuery: "
					+ sb.toString(), null);
			
			Query query = getEntityManager().createQuery(sb.toString());
			
			Map<String, Object> parameters = queryObject.generateParameters();
			for (Entry<String, Object> parameter : parameters.entrySet()) {
				query.setParameter(parameter.getKey(), parameter.getValue());
			}
			
			if (size != 0) {
				query.setMaxResults(size).setFirstResult(page * size);
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name failed", re);
			throw re;
		}
	}
	
	/**
	 * 计数
	 * 
	 * @author wenlele
	 */
	public long countAll() {
		getLogger().log(Level.DEBUG, "count all " + getClassName() + "instances", null);
		try {
			final String queryString = "select count(model)" + " from "
					+ getClassName() + " model";
			Query query = getEntityManager().createQuery(queryString);
			Long count = (Long) query.getSingleResult();
			return count.longValue();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "count all failed", re);
			throw re;
		}
	}

	/**
	 * 查找匹配一个字段值的计数
	 * @param property
	 * @param value
	 * @return
	 */
	public long countByProperty(String property, Object value) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property: "
				+ property + ", value: " + value, null);
		try {
			final String queryString = "select count(model)" + " from "
					+ getClassName() + " model" + " where model." + property
					+ "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			Long count = (Long) query.getSingleResult();
			return count.longValue();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "count by property name failed", re);
			throw re;
		}
	}

	/**
	 * 查找匹配两个字段值的计数
	 * 
	 * @param firstName
	 * @param firstValue
	 * @param secondName
	 * @param secondValue
	 * @return
	 */
	public long countByProperty2(String firstName, Object firstValue,
			String secondName, Object secondValue) {
		getLogger().log(Level.DEBUG, "finding " + getClassName() + " instance with property1: "
				+ firstName + ", value1: " + firstValue + "; propety2: "
				+ secondName + ", value2: " + secondValue, null);
		try {
			final String queryString = "select count(model)" + " from "
					+ getClassName() + " model" + " where model." + firstName
					+ "= :value1" + " and model." + secondName + "=:value2";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value1", firstValue);
			query.setParameter("value2", secondValue);
			Long count = (Long) query.getSingleResult();
			return count.longValue();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "count by property name2 failed", re);
			throw re;
		}
	}
	

	public Long countByProperties(QueryObject queryObject) {
		getLogger().log(Level.DEBUG, "countinging " + getClassName() + " instance with properties: "
				+ queryObject, null);
		try {
			
			StringBuffer sb = new StringBuffer();
			sb.append("select count(model) from " + getClassName() + " model");

			sb.append(queryObject.generateQueryString(new JPAInterpreter()));
			
			getLogger().log(Level.DEBUG, "finding " + getClassName() + " createQuery: "
					+ sb.toString(), null);
			
			Query query = getEntityManager().createQuery(sb.toString());
			
			Map<String, Object> parameters = queryObject.generateParameters();
			for (Entry<String, Object> parameter : parameters.entrySet()) {
				query.setParameter(parameter.getKey(), parameter.getValue());
			}
			Long count = (Long) query.getSingleResult();
			return count.longValue();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name failed", re);
			throw re;
		}
	}
}