/**
 * File: AbstractDAOTest.java
 * Author: weed
 * Create Time: 2012-11-27
 */
package appcloud.dbproxy.util;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.RoutingEntry;
import appcloud.common.proxy.RoutingEntryProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.OrderBean;
import appcloud.common.util.query.OrderBean.OrderBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.model.RoutingEntryTable;
import appcloud.dbproxy.util.sql.JPAInterpreter;


/**
 * @author weed
 *
 */
public class AbstractDAOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RoutingEntryProxy routingEntryProxy = ConnectionFactory.getRoutingEntryProxy();
		List<String> destPrefixes = new ArrayList<String>();
		destPrefixes.add("nctest1");
		destPrefixes.add("nctest2");
		destPrefixes.add("nctest3");
		
		FilterBean<RoutingEntry> fb1 = new FilterBean<RoutingEntry>("srcSuffix", "4lab", FilterBeanType.BOTH_LIKE);
		FilterBean<RoutingEntry> fb2 = new FilterBean<RoutingEntry>("destPrefix", destPrefixes, FilterBeanType.IN);
		FilterBean<RoutingEntry> fb3 = new FilterBean<RoutingEntry>("weight", 3, FilterBeanType.EQUAL);

		OrderBean<RoutingEntry> ob1 = new OrderBean<RoutingEntry>("weight", OrderBeanType.ORDERBY_ASC);
		
		QueryObject<RoutingEntry> queryObject = new QueryObject<RoutingEntry>();

		queryObject.addFilterBean(fb1);
		queryObject.addFilterBean(fb2);
		queryObject.addFilterBean(fb3);
		queryObject.addOrderBean(ob1);
		String query = queryObject.generateQueryString(new JPAInterpreter());
		System.out.println(query);
		
		List<RoutingEntryTable> rets;
		try {
			//rets = (List<RoutingEntryTable>) routingEntryProxy.search(queryObject, 0, 10);
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
