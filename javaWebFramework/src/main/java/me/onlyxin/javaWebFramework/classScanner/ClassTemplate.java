package me.onlyxin.javaWebFramework.classScanner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.onlyxin.javaWebFramework.utils.ClassUtil;
import me.onlyxin.javaWebFramework.utils.PropsUtil;
import me.onlyxin.javaWebFramework.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//抽象模板
public abstract class ClassTemplate {
  
	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(ClassTemplate.class);
	
	protected final String packageName;
	
	protected ClassTemplate(String packageName) {
		this.packageName = packageName;
	}
	
	public final List<Class<?>> getClassList() {
		ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
		try {
			Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace('.', '/'));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					if (protocol.equals("file")) {
						String packagePath = url.getPath();
						addClass(classList, packageName, packagePath);
					} else if (protocol.equals("jar")) {
						JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
						JarFile jarFile = jarURLConnection.getJarFile();
						Enumeration<JarEntry> jarEntries = jarFile.entries();
						while (jarEntries.hasMoreElements()) {
							JarEntry jarEntry = jarEntries.nextElement();
							String jarEntryName = jarEntry.getName();
							if (jarEntryName.endsWith(".class")) {
								String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
								doAddClass(classList, className);
							}
						}
					}
				}
			}
			return classList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取类出错");
			throw new RuntimeException(e);
		}
		
		
	}

	private void addClass(ArrayList<Class<?>> classList, String packageName,
			String packagePath) {
		// TODO Auto-generated method stub
		File[] listFiles = new File(packagePath).listFiles(new FileFilter() {
			
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory() ;
			}
		});
		for (File file : listFiles) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtil.isNotEmpty(packageName)) {
					className = packagePath + "." + className;
				}
				doAddClass(classList, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtil.isNotEmpty(packageName)) {
					subPackagePath = packagePath + "." + subPackagePath;
				}
				String subPackageName = fileName;
				if (StringUtil.isNotEmpty(packageName)) {
					subPackageName = packagePath + "." + subPackageName;
				}
				addClass(classList, subPackageName, subPackagePath);
			}
		}
	}
	//模板方法
	private void doAddClass(ArrayList<Class<?>> classList, String className) {
		// TODO Auto-generated method stub
		Class<?> clazz = ClassUtil.loadClass(className, false);
		if (doCheck(clazz)) {
			classList.add(clazz);
		}
	}
	//抽象方法
	public abstract boolean doCheck(Class<?> clazz);
}
