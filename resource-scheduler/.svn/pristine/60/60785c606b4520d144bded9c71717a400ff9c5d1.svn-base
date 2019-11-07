/**
 * File: ReturnDefaults.java
 * Author: weed
 * Create Time: 2013-5-8
 */
package appcloud.resourcescheduler.stub.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import appcloud.common.errorcode.AREC;
import appcloud.common.errorcode.NAEC;
import appcloud.common.errorcode.NCEC;
import appcloud.common.errorcode.RSEC;
import appcloud.common.errorcode.VMSEC;

/**
 * @author weed
 *
 */
public class ReturnDefaults {
//    private static Set<Class> unsupportedClazzes = new HashSet<Class>();
    private static Map<Class, String> clazzes = new HashMap<Class, String>();
    
    static{
   	    // These gets initialized to their default values
    	
    	//Primitive Defaults
    	clazzes.put(boolean.class, "true");
    	clazzes.put(byte.class, "0");
    	clazzes.put(short.class, "0");
    	clazzes.put(int.class, "0");
    	clazzes.put(long.class, "0");
    	clazzes.put(float.class, "0.0f");
    	clazzes.put(double.class, "0.0");
    	
    	//Non Primitive Defaults
    	clazzes.put(Boolean.class, "true");
    	clazzes.put(Byte.class, "0");
    	clazzes.put(Short.class, "0");
    	clazzes.put(Integer.class, "0");
    	clazzes.put(Long.class, "0");
    	clazzes.put(Float.class, "0.0f");
    	clazzes.put(Double.class, "0.0");
    	
    	//Collection Defaults
    	clazzes.put(List.class, constructReturnValue(ImportTypes.abstractToSubclass(List.class)));
    	clazzes.put(Map.class, constructReturnValue(ImportTypes.abstractToSubclass(Map.class)));
    	clazzes.put(Set.class, constructReturnValue(ImportTypes.abstractToSubclass(Set.class)));
    	
    	//Error code Defaults
    	clazzes.put(AREC.class, "AREC.NO_ERROR");
    	clazzes.put(NCEC.class, "NCEC.NO_ERROR");
    	clazzes.put(NAEC.class, "NAEC.NO_ERROR");
    	clazzes.put(RSEC.class, "RSEC.NO_ERROR");
    	clazzes.put(VMSEC.class, "VMSEC.NO_ERROR");
    }
    
    public static Object getDefaultValue(Class clazz) {
        if (clazzes.containsKey(clazz)) {
            return clazzes.get(clazz);
        }
        else {
//        	if (unsupportedClazzes.add(clazz)){
//                System.out.println(
//                        "Class type " + clazz + " not supported");
//        	}
    		return constructReturnValue(clazz);
        }
    }
    
    public static String constructReturnValue(Class clazz){
    	return "new " + clazz.getSimpleName() + "()";
    }
}
