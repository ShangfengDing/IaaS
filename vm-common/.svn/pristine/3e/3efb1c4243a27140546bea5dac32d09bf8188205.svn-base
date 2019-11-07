package appcloud.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import appcloud.common.model.Admin.AdminRoleEnum;
import appcloud.common.model.Domain.DomainTypeEnum;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.J2EEApp.J2EEAppStatusEnum;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestQueryObjectEnumConverting {
	private static final ObjectMapper mapper = new ObjectMapper();
	private FilterBean filter = null;
	private String str = null;
	private Random rnd = new Random();
	
	private void jsonSerializeAndDeSerialize(String key, Object value, String type) throws IOException {
		filter = new FilterBean(key, value, FilterBeanType.EQUAL);		
		str = mapper.writeValueAsString(filter);
		filter = mapper.readValue(str, FilterBean.class);
		Assert.assertEquals("Convert " + type + " failed", value, filter.getValue());
	}
	
	@Test
	public void testConvertHostTypeEnum() throws IOException {
		String type = "HostTypeEnum";
		
		jsonSerializeAndDeSerialize("type", HostTypeEnum.COMPUTE_NODE, type);
		
		jsonSerializeAndDeSerialize("type", HostTypeEnum.FUNCTION_NODE, type);
	}
	
	@Test
	public void testConvertAminRoleEnum() throws IOException {
		String type = "AdminRoleEnum";
		
		jsonSerializeAndDeSerialize("type", AdminRoleEnum.PLATADMIN, type);
		
		jsonSerializeAndDeSerialize("type", AdminRoleEnum.APPADMIN, type);
	}
	
	@Test
	public void testConvertDomainTypeEnum() throws IOException {
		String type = "DomainTypeEnum";
		
		jsonSerializeAndDeSerialize("type", DomainTypeEnum.INSIDE, type);
		
		jsonSerializeAndDeSerialize("type", DomainTypeEnum.OUTSIDE, type);
		filter = new FilterBean("type", DomainTypeEnum.INSIDE, FilterBeanType.EQUAL);
	}
	
	@Test
	public void testConvertJ2EEAppStatusEnumEnum() throws IOException {
		String type = "J2EEAppStatusEnum";
		
		jsonSerializeAndDeSerialize("status", J2EEAppStatusEnum.SUBMMITED_NOT_VERIFIED, type);
		
		jsonSerializeAndDeSerialize("status", J2EEAppStatusEnum.NOT_UPLOADED, type);
		
		jsonSerializeAndDeSerialize("status", J2EEAppStatusEnum.STOPPED, type);
	}
	
	@Test
	public void testConvertString() throws IOException {		
		String type = "String";
		
		jsonSerializeAndDeSerialize("uuid", "myUUID", type);
	}
	
	@Test
	public void testConvertInteger() throws IOException {
		int id = rnd.nextInt();
		jsonSerializeAndDeSerialize("id", id, "int");
	}
	
	@Test
	public void testConvertDouble() throws IOException {
		double amount = rnd.nextDouble();
		jsonSerializeAndDeSerialize("amount", amount, "double");
	}
	
	@Test
	public void testConvertEnumList() throws IOException {
		List<HostTypeEnum> enumList = new ArrayList<HostTypeEnum>();
		enumList.add(HostTypeEnum.COMPUTE_NODE);
		enumList.add(HostTypeEnum.COMPUTE_NODE);
		filter = new FilterBean("type", enumList, FilterBeanType.IN);		
		str = mapper.writeValueAsString(filter);
		filter = mapper.readValue(str, FilterBean.class);
		
		for (Object o : (List)filter.getValue()) {
			Assert.assertTrue("Enum List Failed", o instanceof HostTypeEnum);
		}
	}
}
