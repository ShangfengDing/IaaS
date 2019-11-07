/**
 * File: FilterBean.java
 * Author: weed
 * Create Time: 2012-11-28
 */
package appcloud.common.util.query;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author weed
 *
 */
/**
 * @author jianglei
 *
 * @param <T>
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class FilterBean<T> {
	private String __name;
	private Object __value;
	private FilterBeanType __type;
	private String __realType;

	public enum FilterBeanType{
		EQUAL,
		NOTEQUAL,
		LESS_THAN,
		MORE_THAN,
		LESS_EQUAL,
		MORE_EQUAL,
		IN,
		LEFT_LIKE,
		RIGHT_LIKE,
		BOTH_LIKE
		;
	}
	
	public FilterBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("rawtypes")
	public FilterBean(String name, Object value, FilterBeanType type) {
		super();
		this.__name = name;		
		this.__value = __format(value, type);
		this.__type = type;
		
		if (__value instanceof Enum) {
			__realType =__value.getClass().getName();
		} else if(__value instanceof Date) {
			__realType = "date";
		} else {
			__realType = "other_types";
			if (__value instanceof List) {
				for (Object o : (List)__value) {
					__realType = o.getClass().getName();
				}
			}
		}
	}

	public String getName() {
		return __name;
	}

	public void setName(String name) {
		this.__name = name;
	}

	@SuppressWarnings("rawtypes")
	public Object getValue() {
		if (__realType.equals("date")) {
			if(__value instanceof Long) {
				__value = new Date((Long) __value);
			}
		}else if (!__realType.equals("other_types")) {
			
			// yo, yo, check null
			if(__value == null) {
				return __value;
			}
			
			// if haven't got serialized, just return it
			if ( __value instanceof Enum) {
				return __value;
			} else if ( __value instanceof List) {
				if (((List)__value).size() == 0) {
					return __value;
				}
					
				Object o = ((List)__value).get(0);
				if ( o instanceof Enum) {
					return __value;
				}					
			}
			
			// now, we are sure that it is an Enum list or Enum
			// and are sure that it has been deserialized to String
			Object realValue = __value;
			try {
				Class<?> clazz = Class.forName(__realType);
				Method m = clazz.getMethod("valueOf", Class.class, String.class);
			
				
				if ( __value instanceof List) {
					List<Object> rList = new ArrayList<Object>();
					
					for (Object o : (List)__value) {
						rList.add(m.invoke(null, clazz, o));
					}
					
					realValue = rList;				
				} else {
					realValue = m.invoke(null, clazz, __value);
				}
			} catch (Exception e) {
				return __value;
			}
			
			return realValue;
		}
		
		return __value;
	}

	public void setValue(Object value) {
		this.__value = value;
	}

	public FilterBeanType getType() {
		return __type;
	}

	public void setType(FilterBeanType type) {
		this.__type = type;
	}
	
	public String getRealType() {
		return __realType;
	}


	/**
	 * <p><em>序列化使用，任何情况下<strong>请不要直接调用此方法</strong></em></p>
	 * 
	 * @param realType
	 */
	public void setRealType(String realType) {
		this.__realType = realType;
	}

	private Object __format(Object value, FilterBeanType type){
		Object formated = null;
		
		switch(type){
		case LEFT_LIKE:
			formated = "%" + value;
			break;
		case RIGHT_LIKE:
			formated = value + "%";
			break;
		case BOTH_LIKE:
			formated = "%" + value + "%";
			break;
		default:
			formated = value;
		}
		
		return formated;
	}

	@Override
	public String toString() {
		return "FilterBean [__name=" + __name + ", __value=" + __value
				+ ", __type=" + __type + "]";
	}
}
