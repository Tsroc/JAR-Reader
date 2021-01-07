package ie.gmit.sw.iterator;

import java.util.ArrayList;
import java.util.List;

import ie.gmit.sw.classable.Classable;

/**
 * A ClassList, used too store Classable objects and iterate over them.
 * 
 * @author Eoin Wilkie. 
 *
 */
public class ClassList implements Container{
	// Make it a singleton.
	private static ClassList instance= null;
	private static List<Classable> classList = null;
	
	/**
	 * Private constructor.
	 * 
	 */
	private ClassList() {
		
	}
	
	/*
	 * Singleton implementation of ClassList.
	 * 
	 */
	public static ClassList getInstance() {
		if (instance == null) {
			instance = new ClassList();
			classList = new ArrayList<>();
		}
		return instance;
	}
	
	/**
	 * Adds a value to the List.
	 * 
	 * @param c The Classable value to be added.
	 */
	public void add(Classable c) {
		classList.add(c);
	}
	
	/**
	 * Gets the ClassList list.
	 * 
	 * @return The ClassList List.
	 */
	public List<Classable> get() {
		return classList;
	}
	
	/**
	 * Prints the ClassList to console.
	 * 
	 */
	public void print() {
		ClassListIterator<Classable> c = getIterator();
		while (c.hasNext()) {
			Classable cls = c.next();
			System.out.println(cls.toString());
		}	
	}
	
	/**
	 * ClassList Iterator.
	 * 
	 * @return ClassListIterator iterator.
	 */
	public ClassListIterator<Classable> getIterator() {
		return new ClassListIteratorImpl(classList); 
	}
	
	/**
	 * Private ClassListIteratorImpl, implements ClassListIterator.
	 * 
	 * @author Eoin Wilkie.
	 *
	 */
	private class ClassListIteratorImpl implements ClassListIterator<Classable>{
		private List<Classable> list;
		private int pos;
		
		/**
		 * Class constructor.
		 * 
		 * @param l Collection to be iterated over.
		 */
		public ClassListIteratorImpl(List<Classable> l) {
			this.list = l;
		}
		
		/**
		 * Iterates to the next value.
		 * 
		 * @return c next value in collection. 
		 * 
		 */
		public Classable next() { 
			Classable c =  list.get(pos); 
			pos ++; 
			return c; 
		} 
	  
		/**
		 * Checks if the next item in the list has a value.
		 * 
		 * @return If value in next, returns true, else returns false.
		 * 
		 */
		public boolean hasNext() { 
			if (pos >= list.size()||list.get(pos) == null) 
				return false; 
			else
				return true; 
		} 
	}
	
}
