/**
 * File: OrderBean.java
 * Author: weed
 * Create Time: 2012-12-1
 */
package appcloud.common.util.query;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author weed
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class OrderBean<T> {
	private String __name;
	private OrderBeanType __type;

	public enum OrderBeanType{
		ORDERBY_ASC,
		ORDERBY_DESC
		;
	}
	
	public OrderBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderBean(String name, OrderBeanType type) {
		super();
		this.__name = name;
		this.__type = type;
	}
	
	public String getName() {
		return __name;
	}

	public void setName(String name) {
		this.__name = name;
	}
	
	public OrderBeanType getType() {
		return __type;
	}

	public void setType(OrderBeanType type) {
		this.__type = type;
	}
}
