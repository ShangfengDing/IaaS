/**
 * File: ClassExtractor.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.stub.builder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * @author weed
 *
 */
public class ClassExtractor {

	public static List<Class<?>> extract(String packageName, boolean recursive) {
		List<Class<?>> clazzes = new LinkedList<Class<?>>();
		initClasses(packageName, recursive, clazzes);
		return clazzes;
	}

	/**
	 * 获取包下的所有类
	 * 
	 * @param pack
	 * @return
	 */
	protected static void initClasses(String pack, boolean recursive,
			List<Class<?>> clazzes) {
		// boolean recursive = true;
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					getClassByFile(packageName, filePath, recursive, clazzes);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param pkgName
	 *            包名
	 * @param pkgPath
	 *            包路径
	 * @param recursive
	 *            是否迭代
	 */
	protected static void getClassByFile(String pkgName, String pkgPath,
			final boolean recursive, List<Class<?>> clazzes) {
		File dir = new File(pkgPath);
		if (!dir.exists() || !dir.isDirectory()) {

			return;
		}

		File[] dirfiles = dir.listFiles(new FileFilter() {

			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});

		for (File file : dirfiles) {
			// 是目录则继续迭代
			if (file.isDirectory()) {
				getClassByFile(pkgName + "." + file.getName(),
						file.getAbsolutePath(), recursive, clazzes);
			} else {

				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					Class<?> clazz = Thread.currentThread()
							.getContextClassLoader()
							.loadClass(pkgName + '.' + className);
					// System.out.println(clazz);
					clazzes.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}