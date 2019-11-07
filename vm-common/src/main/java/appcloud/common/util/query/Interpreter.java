/**
 * File: Interpreter.java
 * Author: weed
 * Create Time: 2012-11-28
 */
package appcloud.common.util.query;

import java.util.List;

/**
 * @author weed
 *
 */
public interface Interpreter {
	public String interpret(FilterBean filterBean);
	
	public String interpret(Filter filter);
	
	public String interpret(OrderBean orderBeans);
}
