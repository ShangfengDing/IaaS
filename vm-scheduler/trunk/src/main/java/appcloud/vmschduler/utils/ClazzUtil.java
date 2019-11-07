package appcloud.vmschduler.utils;

public class ClazzUtil {

	public static Object getInstance(String clazzName) {
		try {
			Class<?> clazz = Class.forName(getFullClazzName(clazzName));
			Object instance  = clazz.newInstance();
			return instance;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFullClazzName(String clazzName) {
		if(clazzName.contains(".")) {
			return clazzName;
		} else {
			return "appcloud.vmscheduler.strategy." + clazzName;
		}
	}
}
