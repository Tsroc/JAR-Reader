package ie.gmit.sw.database;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ie.gmit.sw.classable.Classable;
import ie.gmit.sw.classable.JavaClassInfo;
import ie.gmit.sw.iterator.ClassList;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * Database uses Java Microstream.
 * 
 * @author Eoin Wilkie.
 *
 */
public class Database {
	private EmbeddedStorageManager db = null; 
	private List<Classable> root = new ArrayList<>(); 
	
	/**
	 * Class constructor.
	 * 
	 */
	public Database() {
		root = ClassList.getInstance().get(); //Initialise the database. Comment out after first use
		db = EmbeddedStorage.start(root, Paths.get("./data")); 
		db.storeRoot(); 
	}
	
	/**
	 * Performs a database query. 
	 * 
	 * @return The number of classes contained in the JAR file.
	 */
	public long queryCLS() {
		return root.stream().count();
	}

	/**
	 * Performs a database query. 
	 * 
	 * @return The number of methods per class in the JAR file.
	 */
	public Object queryWMC() {
		Map<String, Collection<String>> classes = root.stream()
										.collect(Collectors.toMap(Classable::getName, Classable::getMethods));
		
		// Build string.
		String str = "";
		for (Entry<String, Collection<String>> entry : classes.entrySet())  
				str += String.format("[%s], methods: %d\n",
							entry.getKey(), entry.getValue().size()); 
		return str;
	}

	/**
	 * Performs a database query. 
	 * 
	 * @return The number of number of children in a tree, from classes in the JAR file.
	 */
	public Object queryNOC() {
		Map<String, Long> classes = root.stream()
										.filter(t->t instanceof JavaClassInfo)
										.filter(t->	!((JavaClassInfo) t).getSuperclass().equals("java.lang.Object"))
										.filter(t->	!((JavaClassInfo) t).getSuperclass().equals("java.lang.Enum"))
										.collect(Collectors.groupingBy(Classable::getSuperclass, Collectors.counting()));
		
		// Build string.
		String str = "";
		for (Entry<String, Long> entry : classes.entrySet())  
				str += String.format("[%s], children: %s\n",
							entry.getKey(), entry.getValue()); 
		return str;
	}
	
	/**
	 * Shuts down the database when the class is garbage collected.
	 * 
	 */
	protected void finalize(){
		db.shutdown(); 
	}
}
