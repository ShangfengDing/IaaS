/**
 * File: AAutoBuilder.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.stub.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import appcloud.resourcescheduler.stub.builder.TemplateConf.PatternType;

/**
 * @author weed
 * 
 */
public class AAutoBuilder {

	private static TemplateConf conf;
	private final static String SRC_PATH = System.getProperty("user.dir")
			+ "/src/test/java/";
	private final static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	static {
		try {
			conf = new TemplateConf("Stub.template");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		boolean force = false;
		build("appcloud.common.service", "appcloud.resourcescheduler.stub.services", force);
		build("appcloud.common.proxy", "appcloud.resourcescheduler.stub.dbproxys", force);
	}
	
	public static void build(String sourcePackage, String targetPackage, boolean force) throws IOException{
		List<Class<?>> clazzes = ClassExtractor.extract(
				sourcePackage, false);
		
		for (Class<?> clazz : clazzes) {
			build(clazz, targetPackage, force);
		}
		
		Map<String, String> mapper = new HashMap<String, String>();
		for (Class<?> clazz : clazzes) {
			mapper.put(clazz.getName(), targetPackage + "." + clazz.getSimpleName() + "Stub");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		File stubsFile = new File(SRC_PATH + targetPackage.replaceAll("[.]", "/") + "/" + sourcePackage + ".stubs.json");
		if (!stubsFile.exists()){
			stubsFile.createNewFile();
		}
		objectMapper.writeValue(stubsFile, mapper);
	}

	private static void build(Class<?> clazz, String targetPackage, boolean force)
			throws IOException {
		String targetClassName = clazz.getSimpleName() + "Stub";
		
		String fileName = SRC_PATH + targetPackage.replaceAll("[.]", "/") + "/"
				+ targetClassName + ".java";
		// System.out.println(fileName);
		File fileWrite = new File(fileName);
		if (!fileWrite.exists()) {
			fileWrite.createNewFile();
		}
		else if (!force){
			return;
		}
		
		Map<PatternType, String> contents = new HashMap<PatternType, String>();
		contents.put(PatternType.UPDATETIME, df.format(new Date()));
		contents.put(PatternType.PACKAGE, targetPackage);
		contents.put(PatternType.CLASS, targetClassName);
		contents.put(PatternType.INTERFACE, clazz.getSimpleName());

		ImportTypes importTypes = new ImportTypes();
		importTypes.add(clazz);

		StringBuilder methods = new StringBuilder();
		for (Method m : clazz.getMethods()) {
			importTypes.add(m.getParameterTypes());
			importTypes.add(m.getExceptionTypes());
			importTypes.add(m.getReturnType());

			String[] parts = m.toString().replaceFirst("abstract ", "")
					.replaceAll("(\\w+\\.)*(\\w+)", "$2")
					.replaceAll("\\w+\\$", "").split("[()]");

			StringBuilder params = new StringBuilder();
			params.append('(');
			if (!parts[1].trim().isEmpty()) {
				int i = 0;
				for (String paramType : parts[1].split(",")) {
					if (i > 0) {
						params.append(", ");
					}
					params.append(paramType).append(' ').append("arg")
							.append(i++);
				}
			}
			params.append(')');

			methods.append("\t@Override\n\t").append(parts[0])
					.append(params.toString());
			if (parts.length == 3) {
				methods.append(parts[2]);
			}

			methods.append("{\n");
			Class<?> ret = m.getReturnType();
			if (!ret.equals(void.class)) {
//				if (!ret.isPrimitive()) {
//					methods.append("\t\treturn null;\n");
//				} else 
				{
					methods.append("\t\treturn " + ReturnDefaults.getDefaultValue(ret) + ";\n");
				}
			}

			methods.append("\t}\n");
		}
		contents.put(PatternType.METHODS, methods.toString());

		StringBuilder imports = new StringBuilder();
		for (String importType : importTypes.getTypeFullNames()) {
			imports.append("import ").append(importType).append(";\n");
		}
		contents.put(PatternType.IMPORTS, imports.toString());

		String output = conf.composing(contents);
		// System.out.println(output);
		FileOutputStream out = new FileOutputStream(fileWrite, false);

		out.write(output.getBytes("utf-8"));

		out.close();
	}
}

