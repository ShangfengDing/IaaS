/**
 * File: MySQLAlertMsgProxyTest.java
 * Author: weed
 * Create Time: 2013-6-20
 */
package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.AlertMsg;
import appcloud.common.model.AlertMsg.AlertMsgStatus;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.OrderBean;
import appcloud.common.util.query.OrderBean.OrderBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.MySQLAlertMsgProxy;

/**
 * @author weed
 *
 */
public class MySQLAlertMsgProxyTest {
	
	static MySQLAlertMsgProxy proxy = null;
	static List<? extends AlertMsg> alertMsgs = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLAlertMsgProxy();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testSave() {
		try {
			proxy.save(new AlertMsg("dbproxy", "alert message test.", "alert message detail test.", new Timestamp(System.currentTimeMillis()), AlertMsgStatus.ALERTED));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testSearchAllQueryObjectOfAlertMsgIntegerInteger() {
		QueryObject<AlertMsg> queryObject = new QueryObject<AlertMsg>();
		FilterBean<AlertMsg> filterBean = new FilterBean<AlertMsg>("status", AlertMsgStatus.ALERTED, FilterBeanType.EQUAL);
		queryObject.addFilterBean(filterBean);
		
		OrderBean<AlertMsg> orderBean = new OrderBean<AlertMsg>("time", OrderBeanType.ORDERBY_ASC);
		queryObject.addOrderBean(orderBean);
		try {
			alertMsgs = proxy.searchAll(queryObject , 0, 50);
			System.out.println(alertMsgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		try {
			if (alertMsgs != null){
				System.out.println(alertMsgs);
				for (AlertMsg alertMsg : alertMsgs){
					alertMsg.setStatus(AlertMsgStatus.INFOED);
					proxy.update(alertMsg);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
