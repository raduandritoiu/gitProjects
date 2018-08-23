package integration.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

public class LibraryLoader {
	private static final String MAIN_CLASS = "integration.controller.LibStart";
	private static final String MAIN_FUNC = "startLibrary";
	
	private URL _url;
	
	
	public LibraryLoader(String urlStr) throws IOException {
		_url = new URL("jar", "", urlStr  + "!/");
	}
	
	
	public int load(String urlStr)
	{
		int ret = 0;
		try {
			ret = 0;
			
			_url = new URL("jar", "", urlStr  + "!/");
			URLClassLoader clsLoader = new URLClassLoader(new URL[] {_url});
			
			URL u = clsLoader.getURLs()[0];
			Class cls = clsLoader.loadClass(MAIN_CLASS);
			Method m = cls.getMethod(MAIN_FUNC);
			m.setAccessible(true);
			int mods = m.getModifiers();
			
			if (m.getReturnType() != void.class || !Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
				System.out.println("static " + !Modifier.isStatic(mods));
				System.out.println("public " + !Modifier.isPublic(mods));
				System.out.println("FUCK! 0");
				return -1;
			}
			
			m.invoke(null);
		}
		catch (IOException e) {
			System.out.println("FUCK! 1");
			ret = -1;
		}
		catch (ClassNotFoundException e) {
			System.out.println("FUCK! 2");
			ret = -1;
		}
		catch (NoSuchMethodException e) { 
			System.out.println("FUCK! 3");
			ret = -1;
		}
		catch (InvocationTargetException e) { 
			System.out.println("FUCK! 4");
			ret = -1;
		}
		catch (IllegalAccessException e) {
			System.out.println("FUCK! 5");
			ret = -1;
		}
		
		return ret;
	}
}
