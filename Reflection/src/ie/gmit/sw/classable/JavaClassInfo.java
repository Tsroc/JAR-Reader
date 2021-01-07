package ie.gmit.sw.classable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Extension of JavaAbstractInfo, for classes.
 * 
 * @author Eoin Wilkie 
 *
 */
public class JavaClassInfo extends JavaAbstractInfo {
	Collection<String> interfaces;
	Collection<String> fields;
	Collection<String> constructors;
	
	/**
	 * Constructor. 
	 * Initializes the collections.
	 * 
	 */
	public JavaClassInfo() {
		interfaces = new ArrayList<>();
		fields = new ArrayList<>();
		constructors = new ArrayList<>();
	}
	
	
	/**
	 * Adds implemented interfaces as a String.
	 * 
	 * @param i The classes interface.
	 */
	public void addInterface(String i) {
		interfaces.add(i);
	}

	/**
	 * Returns the list of interfaces as a String.
	 * 
	 * @return Interfaces as String.
	 */
	public String getInterfaces() {
		String str = "";
		
		for(String i: interfaces)
			str += "\n\t" + i.toString();			
		
		return str;
	}
	
	/**
	 * Adds a field. 
	 * 
	 * @param f The filed to be added.
	 */
	public void addField(String f) {
		fields.add(f);
	}

	/**
	 * Returns the list of fields as a String.
	 * 
	 * @return Fields as String.
	 */
	public String getFields() {
		String str = "";
		
		for(String f: fields)
			str += "\n\t" + f.toString();
		
		return str;
	}


	/**
	 * Adds a constructor. 
	 * 
	 * @param f The constructor to be added.
	 */
	public void addConstructor(String c) {
		constructors.add(c);
	}

	/**
	 * Returns the list of constructors as a String.
	 * 
	 * @return Constructors as String.
	 */
	public String getConstructors() {
		String str = "";
		
		for(String c: constructors)
			str += "\n\t" + c.toString();
		
		return str;
	}
	
	/**
	 * Returns ToString.
	 * 
	 */
	public String toString() {
		return super.toString() + ", " + getSuperclass();
	}

}
