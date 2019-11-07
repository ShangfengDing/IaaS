/**
 * File: QueryObject.java
 * Author: weed
 * Create Time: 2012-11-28
 */
package appcloud.common.util.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.Filter.FilterType;

/**
 * @author weed
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class QueryObject<T> {
	private Filter<T> __filter = null;
	private List<OrderBean> __orderBeans = new ArrayList<OrderBean>();
	
	public QueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void addFilterBean(FilterBean<T> filterBean, FilterLogic logical){
		if (__filter == null){
			__filter = new Filter<T>(filterBean);
		}
		else{
			Filter<T> newfilter = new Filter<T>(__filter, new Filter<T>(filterBean), logical);
			__filter = newfilter;
		}
	}
	
	public void addFilterBean(FilterBean<T> filterBean){
		if (__filter == null){
			__filter = new Filter<T>(filterBean);
		}
		else{
			Filter<T> newfilter = new Filter<T>(__filter, new Filter<T>(filterBean), FilterLogic.AND);
			__filter = newfilter;
		}
	}
	
	public void addOrderBean(OrderBean<T> orderBean){
		__orderBeans.add(orderBean);
	}
	
	public String generateQueryString(Interpreter interpreter){
		StringBuffer sb = new StringBuffer();
		if (__filter != null){
			sb.append(" where");
			sb.append(interpreter.interpret(__filter));
		}
		if (!__orderBeans.isEmpty()){
			sb.append(" order by");
			Iterator<OrderBean> iter = __orderBeans.iterator();
			while (iter.hasNext()){
				OrderBean orderBean = iter.next();
				sb.append(interpreter.interpret(orderBean));
				if (iter.hasNext()){
					sb.append(" ,");
				}
			}
			
		}
		return sb.toString();
	}
	
	public Map<String, Object> generateParameters(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (__filter != null){
			generateParameters(parameters, __filter);
		}
		return parameters;
	}

	private void generateParameters(Map<String, Object> parameters, Filter<T> filter){
		FilterType type = filter.getType();
		if (0 == type.compareTo(FilterType.LEAF)){
			FilterBean<T> fb = filter.getFilterBean();
			parameters.put(fb.getName(), fb.getValue());
			return;
		}
		else{
			generateParameters(parameters, filter.getLeftFilter());
			generateParameters(parameters, filter.getRightFilter());
		}
		
	}

	public Filter<T> getFilter() {
		return __filter;
	}


	public void setFilter(Filter<T> filter) {
		this.__filter = filter;
	}


	public List<OrderBean> getOrderBeans() {
		return __orderBeans;
	}


	public void setOrderBeans(List<OrderBean> orderBeans) {
		this.__orderBeans = orderBeans;
	}


	@Override
	public String toString() {
		return "QueryObject [__filter=" + __filter + ", orderBeans="
				+ __orderBeans + "]";
	}
}
