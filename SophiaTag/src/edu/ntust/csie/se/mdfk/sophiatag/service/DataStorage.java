package edu.ntust.csie.se.mdfk.sophiatag.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class DataStorage {
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private static final String FILE_PATH = "record.dat";
	
	private File file;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public DataStorage() {
		file = new File(FILE_PATH);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void saveData(NecessaryData data) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
			outStream.writeObject(data);
			outStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public NecessaryData loadData() {
		if (!file.exists()) {
			return null;
		}
		
		NecessaryData data = null;
		
		try {
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file));
			data = (NecessaryData) inStream.readObject();
			inStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static class NecessaryData implements Serializable{
		private final String rootDirectory;
		private final MaterialPool pool;
		private final MaterialSearcher searcher;
		
		public NecessaryData(String rootDirectory, MaterialPool pool, MaterialSearcher searcher) {
			this.rootDirectory = rootDirectory;
			this.pool = pool;
			this.searcher = searcher;
			
		}

		public String getRootDirectory() {
			return rootDirectory;
		}

		public MaterialPool getPool() {
			return pool;
		}

		public MaterialSearcher getSearcher() {
			return searcher;
		}
		
		
		
	}
}
