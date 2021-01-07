package ie.gmit.sw.iterator;

import ie.gmit.sw.classable.Classable;

/**
 * The iterator design pattern allows you to access objects that are stored in collection types.
 *
 * @author Eoin Wilkie
 * @version 1.0
 */

public interface ClassListIterator<E> {
	/**
	 * Checks if the next item in the list has a value.
	 * 
	 * @return If value in next, returns true, else returns false 
	 * 
	 */
	public boolean hasNext();
	
	/**
	 * Iterates to the next value.
	 * 
	 * @return next value in collection. 
	 * 
	 */
	public Classable next();

}
