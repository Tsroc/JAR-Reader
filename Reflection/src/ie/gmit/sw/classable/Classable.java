package ie.gmit.sw.classable;

import java.util.Collection;

/**
 * Classable interface, used to retain information from classes read by the JAR reader.
 * 
 * @author Eoin Wilkie 
 *
 */
public interface Classable{
	public void setName(String n);
	public String getName();
	public void setPackage(String p);
	public String getPackage();
	public String getSuperclass();
	public void setSuperclass(String s);
	public void addMethod(String m);
	public Collection<String> getMethods();
	public void printMethods();

}
