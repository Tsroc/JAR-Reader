package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Runner {

	public static void main(String args[])  throws Exception{
		String fname = "lib/Assessment2.jar";
		
		JarReader readJarFile = new JarReader(fname);
		readJarFile.process();

	}

}