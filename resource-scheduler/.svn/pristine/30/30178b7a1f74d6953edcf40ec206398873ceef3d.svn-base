/**
 * File: ImportTypes.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.stub.builder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author weed
 *
 */
public class ImportTypes {
	private Set<Class<?>> importTypes = new HashSet<Class<?>>();
	private static Map<Class<?>, Class<?>> abstractClazzes = new HashMap<Class<?>, Class<?>>();

	static{
		abstractClazzes.put(List.class, ArrayList.class);
		abstractClazzes.put(Map.class, HashMap.class);
		abstractClazzes.put(Set.class, HashSet.class);
	}
	
	public static Class<?> abstractToSubclass(Class<?> clazz){
		if (abstractClazzes.containsKey(clazz)){
			return abstractClazzes.get(clazz);
		}
		else{
			return null;
		}
	}
	
	public void add(Class<?> clazz) {
		if (!clazz.isPrimitive()) {
			importTypes.add(clazz);
			if (abstractClazzes.containsKey(clazz)){
				importTypes.add(abstractClazzes.get(clazz));
			}
		}
	}
	
	public void add(Class<?>[] clazzes){
		for (Class<?> clazz : clazzes){
			add(clazz);
		}
	}

	public Set<String> getTypeFullNames() {
		Set<String> typeFullNames = new HashSet<String>();
		for (Class<?> clazz : importTypes) {
			typeFullNames.add(clazz.getName().replaceAll("\\$", "\\."));
		}
		return typeFullNames;
	}
}