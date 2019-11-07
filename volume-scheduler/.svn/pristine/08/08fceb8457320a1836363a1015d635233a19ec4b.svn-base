package appcloud.vs.util;

import org.apache.log4j.Logger;

import appcloud.common.model.ResourceStrategy;
import appcloud.vs.impl.DBUtil;
import appcloud.vs.strategy.HostFilterHandler;
import appcloud.vs.strategy.SelectorService;

public class Name2Class {
	private static final String PACKAGE_PATH = "appcloud.vs.strategy.";
	private static Logger logger = Logger.getLogger(Name2Class.class);
	public static SelectorService getInstance(Integer clusterId) throws Exception{
		try {
			ResourceStrategy resourceStrategy = DBUtil.getInstance().getDiskResourceStrategy(clusterId);
			String clazzName = resourceStrategy.getClazzs();
			Class<?> clazz =  Class.forName(PACKAGE_PATH+clazzName);
			return (SelectorService) clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
