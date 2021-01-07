package ie.gmit.sw;

import java.util.jar.*;

import ie.gmit.sw.classable.Classable;
import ie.gmit.sw.classable.JavaInterfaceInfo;
import ie.gmit.sw.iterator.ClassList;
import ie.gmit.sw.classable.JavaClassInfo;

import java.util.Enumeration;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Reads a JAR file, adding relevant details to ClassList.
 * 
 * @author Eoin Wilkie 
 *
 */
public class JarReader {
// JAR reader info: https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
	String fname;
	JarFile jarFile;
	ClassList classList; 
	
	/**
	 *	Private Class constructor. 
	 *	Sets the ClassList to the singleton instance of ClassList	 *	
	 *
	 */
	private JarReader() {
		classList = ClassList.getInstance(); 
	}
	
	/**
	 * Class Constructor
	 * Allows the user to enter a String.
	 * 
	 * @param f The String to read.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public JarReader(String f) throws FileNotFoundException, IOException {
		this();
		this.fname = f;
		this.jarFile = new JarFile(f);
	}
	
	/**
	 * Class Constructor
	 * Allows the user to enter a File.
	 * 
	 * @param f The File to read.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public JarReader(File f) throws FileNotFoundException, IOException {
		this();
		this.fname = f.getAbsolutePath();
		this.jarFile = new JarFile(f);
	}
	
	/**
	 * Processes the JAR reading,
	 * extracts class info for each class in the JAR,
	 * and adds relevant info to the ClassList.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void process() throws IOException, ClassNotFoundException{
		Enumeration<JarEntry> e = jarFile.entries();
		URL[] urls = { new URL("jar:file:" + fname + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);
		
		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    if(je.isDirectory() || !je.getName().endsWith(".class")){
		        continue;
		    }
		    // -6 because of .class
		    // Clean this up!!!
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    
		    Class<?> cls = cl.loadClass(className);
		    addClass(cls);
		   
		    // https://stackoverflow.com/questions/5712839/easy-way-to-count-lines-of-code-using-reflection
		    
		}
		
	}
	
	/**
	 * Adds Class information to ClassList.
	 * 
	 * @param cls The Class to be added.
	 */
	private void addClass(Class<?> cls) {
		Classable newCls;
		
		// Checks if the class is an interface or a class,
		if(cls.isInterface()) {
			// Creating an InterfaceClass
			newCls = new JavaInterfaceInfo();
		
		} else {
			// Creating a StandardClass
			newCls = new JavaClassInfo();
			
		}
		
		// Base Class: name, package, methods
		newCls.setName(cls.getName());
		newCls.setPackage(cls.getPackage().toString());

		Method[] methods = cls.getMethods(); 
		for(Method m: methods) {
			newCls.addMethod(m.toString());
		}
		
		
		if(newCls instanceof JavaClassInfo) {
			// Standard class: interface, fields, constructors
			((JavaClassInfo) newCls).setSuperclass(cls.getSuperclass().toString());
			
			Class[] interfaces = cls.getInterfaces();
			for(Class i: interfaces)
				((JavaClassInfo) newCls).addInterface(i.toString());
			
			// Field
			Field[] fields = cls.getFields();
			for(Field f: fields)
				((JavaClassInfo) newCls).addField(f.toString());
			
			// Constructor
			Constructor[] cons = cls.getConstructors(); 
			for(Constructor c: cons)
				((JavaClassInfo) newCls).addConstructor(c.toString());
			
		}
		classList.add(newCls);
	}
}
