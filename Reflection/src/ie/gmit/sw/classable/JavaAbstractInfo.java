package ie.gmit.sw.classable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Retains relevant information from JAR read java classes.
 * 
 * @author Eoin Wilkie 
 *
 */
public abstract class JavaAbstractInfo implements Classable {
	String name;
	String pack; 
	String superclass;
	Collection<String> methods;
	
	/**
	 * Constructor.
	 * 
	 */
	public JavaAbstractInfo() {
		methods = new ArrayList<>();
	}

	/**
	 * Sets the name of the class.
	 * 
	 * @param n The name to be set. 
	 * 
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Gets the name of the class.
	 * 
	 * @return Class name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the package of the class.
	 * 
	 * @param p The package to be set. 
	 * 
	 */
	public void setPackage(String p) {
		pack = p;
	}
	
	/**
	 * Gets the class package as a String.
	 * 
	 * @return Class package.
	 */
	public String getPackage() {
		return pack;
	}

	/**
	 * Sets the superclass of the class.
	 * 
	 * @param s The superclass to be set. 
	 * 
	 */
	public void setSuperclass(String s) {
		superclass = s.replaceFirst("class", "").trim();
	}
	
	/**
	 * Gets the class superclass as a String.
	 * 
	 * @return Class superclass. 
	 */
	public String getSuperclass() {
		return superclass;
	}
	
	/**
	 * Adds a method to the class.
	 * 
	 * @param m The method to add. 
	 * 
	 */
	public void addMethod(String m) {
		if(!m.contains("java.lang.Object")&&!m.contains("java.lang.Enum")) {
			methods.add(m);
		}
	}

	/**
	 * Gets the class methods as a collection of Strings.
	 * 
	 * @return The class methods.
	 */
	public Collection<String> getMethods() {
		return methods;
	}

	/**
	 * Prints the methods of the class.
	 * 
	 */
	public void printMethods() {
		for(String m: methods)
			System.out.println(m.toString());
	}
	
	/**
	 * To String method.
	 */
	public String toString() {
		return String.format("Name: %s, Package: %s",
				getName(), getPackage());
	}
	
}
