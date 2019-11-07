/**
 * File: JPAInterpreter.java
 * Author: weed
 * Create Time: 2012-11-28
 */
package appcloud.dbproxy.util.sql;

import java.util.Iterator;
import java.util.List;

import appcloud.common.util.query.Filter;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.Interpreter;
import appcloud.common.util.query.OrderBean;
import appcloud.common.util.query.Filter.FilterType;



/**
 * @author weed
 *
 */
public class JPAInterpreter implements Interpreter {

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.Interpreter#interpret(appcloud.dbproxy.util.sql.FilterBean)
	 */
	@Override
	public String interpret(FilterBean filterBean) {
		// TODO Auto-generated method stub
		String ret = null;
		String name = filterBean.getName();
		switch(filterBean.getType()){
		case EQUAL:
			ret = " model." + name + " = :" + name;
			break;
		case NOTEQUAL:
			ret = " model." + name + " <> :" + name;
			break;
		case LESS_THAN:
			ret = " model." + name + " < :" + name;
			break;
		case MORE_THAN:
			ret = " model." + name + " > :" + name;
			break;
		case LESS_EQUAL:
			ret = " model." + name + " <= :" + name;
			break;
		case MORE_EQUAL:
			ret = " model." + name + " >= :" + name;
			break;
		case IN:
			ret = " model." + name + " in (:" + name + ")";
			break;
		case LEFT_LIKE: case RIGHT_LIKE: case BOTH_LIKE: 
			ret = " model." + name + " like :" + name;
			break;
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.Interpreter#interpret(appcloud.dbproxy.util.sql.Filter)
	 */
	@Override
	public String interpret(Filter filter) {
		// TODO Auto-generated method stub
		if (null == filter){
			return "";
		}
		
		FilterType type = filter.getType();
		if (0 == type.compareTo(FilterType.LEAF)){
			return interpret(filter.getFilterBean());
		}
		else if (0 == type.compareTo(FilterType.NOTLEAF)){
			String opr = null;
			switch (filter.getOperater()){
			case AND:
				opr = " and ";
				break;
			case OR:
				opr = " or ";
				break;
			}
			return " ( " + interpret(filter.getLeftFilter()) + opr + interpret(filter.getRightFilter()) + " )";
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.util.sql.Interpreter#interpret(appcloud.common.util.sql.OrderBean)
	 */
	@Override
	public String interpret(OrderBean orderBean) {
		// TODO Auto-generated method stub
		String ret = null;
		switch (orderBean.getType()){
		case ORDERBY_ASC:
			ret = " model." + orderBean.getName() + " asc";
			break;
		case ORDERBY_DESC:
			ret = " model." + orderBean.getName() + " desc";
			break;
		}
		return ret;
	}

}
