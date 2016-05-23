package oraclient.io;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClass {
    public LoadClass() {
    }
    
    public String jarFilePath(String filePath) {
        String sep = System.getProperty("file.separator");
        StringBuffer env = new StringBuffer(System.getenv("CLIENT_HOME"));
        if (!env.toString().endsWith(sep)) {
            env.append(sep);
        }
        env.append(filePath + sep);
        return env.toString();
    }
    
    public void addClass(final File file) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (MalformedURLException e) {
        } catch (IllegalAccessException e) {
        }
    }
}
