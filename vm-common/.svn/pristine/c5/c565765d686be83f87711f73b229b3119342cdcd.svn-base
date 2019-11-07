/**
 * File: Filter.java
 * Author: weed
 * Create Time: 2012-11-29
 */
package appcloud.common.util.query;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author weed
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Filter<T> {
	private Filter<T> __leftFilter = null;
	private Filter<T> __rightFilter = null;
	private FilterBean<T> __filterBean = null;
	private FilterLogic __operater = null;
	private FilterType __type;
	
	public enum FilterLogic{
		AND,
		OR
	}
	
	public enum FilterType{
		LEAF,
		NOTLEAF
	}
	
	public Filter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Filter(FilterBean<T> filterBean) {
		// TODO Auto-generated constructor stub
		super();
		this.__type = FilterType.LEAF;
		this.__filterBean = filterBean;
	}

	public Filter(Filter<T> leftFilter, Filter<T> rightFilter, FilterLogic operater) {
		super();
		this.__type = FilterType.NOTLEAF;
		this.__leftFilter = leftFilter;
		this.__rightFilter = rightFilter;
		this.__operater = operater;
	}

	public Filter<T> getLeftFilter() {
		return __leftFilter;
	}

	public void setLeftFilter(Filter<T> leftFilter) {
		this.__leftFilter = leftFilter;
	}

	public Filter<T> getRightFilter() {
		return __rightFilter;
	}

	public void setRightFilter(Filter<T> rightFilter) {
		this.__rightFilter = rightFilter;
	}

	public FilterBean<T> getFilterBean() {
		return __filterBean;
	}

	public void setFilterBean(FilterBean<T> filterBean) {
		this.__filterBean = filterBean;
	}

	public FilterLogic getOperater() {
		return __operater;
	}

	public void setOperater(FilterLogic operater) {
		this.__operater = operater;
	}

	public FilterType getType() {
		return __type;
	}

	public void setType(FilterType type) {
		this.__type = type;
	}

	@Override
	public String toString() {
		return "Filter [leftFilter=" + __leftFilter + ", rightFilter="
				+ __rightFilter + ", filterBean=" + __filterBean + ", operater="
				+ __operater + ", type=" + __type + "]";
	}

}
