package ie.gmit.sw;

import java.util.jar.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


// JAR reader info: https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar


public class JarReader {
	String fname;
	JarFile jarFile;
	
	public JarReader(String fname) throws FileNotFoundException, IOException {
		this.fname = fname;
		this.jarFile = new JarFile(fname);
	}
	
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
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    
		    Class<?> cls = cl.loadClass(className);
		    getClassDetails(cls);
		    
		}
	}
	
	private void getClassDetails(Class<?> cls) {
		// Process class
		// Print class name
		System.out.println(cls.getName());
		// Print package
		Package pack = cls.getPackage(); 
		System.out.println(pack.toString());
		
		// Is interface?
		boolean iface = cls.isInterface();
		System.out.println("Interface: " + iface);
		
		// Get the set of interface it implements
		Class[] interfaces = cls.getInterfaces();
		System.out.println("Interfaces::" + interfaces.length);
		for(Class i: interfaces)
			System.out.println("\t" + i);
		
		// Get the set of constructors
		Constructor[] cons = cls.getConstructors(); 
		System.out.println("Constructors:" + cons.length);
		for(Constructor c: cons) {
			System.out.print("\t" + c);
			// Print the parameters
			Class[] param = c.getParameterTypes();
			System.out.println(", " + Arrays.toString(param));	
		}
			
		//Get the fields / attributes
		Field[] fields = cls.getFields();
		System.out.println("Fields: " + fields.length);
		for(Field f: fields) {
			System.out.print("\t" + f);
		}
		
		// Get the set of methods
		Method[] methods = cls.getMethods(); 
		System.out.println("Methods: " + methods.length);
		for(Method m: methods) {
			System.out.println("\t" + m);
			// Print the return type 
			Class c = m.getReturnType();
			System.out.println("\t\treturn: " + m);	
			// Print method params
			Class[] params = m.getParameterTypes();
			System.out.println("\t\tparams: " + Arrays.toString(params));	
			
		}
		System.out.println();

	}
		

}
