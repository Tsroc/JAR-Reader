package ie.gmit.sw.iterator;

import ie.gmit.sw.classable.Classable;

/**
 * A container interface which iterates over a Classable collection. 
 * 
 * @author Eoin Wilkie 
 *
 */
public interface Container {
	/**
	 * Iterates over a Classable collection.
	 * 
	 * @return ClassListIterator iterator.
	 */
	public ClassListIterator<Classable> getIterator();
}
