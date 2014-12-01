package edu.ntust.csie.se.mdfk.sophiatag.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import edu.ntust.csie.se.mdfk.sophiatag.data.Material;

import java.util.List;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class MaterialScanner {
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public File rootDirectory;
	

	/**
	 * constructor
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public MaterialScanner(String rootDirectory) {	
		this.rootDirectory = new File(rootDirectory);
		// TODO construct me	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void setRootDirectory(String directory) {
		this.rootDirectory = new File(directory);	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String getRootDirectory() {
		return this.rootDirectory.getAbsolutePath();	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public List<Material> newScan() {
		return this.convertToMaterials(rawScan());
	}
	
	private List<Material> convertToMaterials(Collection<File> files) {
		LinkedList<Material> materials = new LinkedList<Material>();
		for (File file: files) {
			materials.add(new Material(file));
		}
		
		return materials;
	}
	
	private List<File> rawScan() {
		final List<File> result = new LinkedList<File>();
		
		try {
			Files.walkFileTree(this.rootDirectory.toPath(), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					result.add(file.toFile().getAbsoluteFile());
					return FileVisitResult.CONTINUE;
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void updateMaterialPool(MaterialPool pool) {
		Set<File> newFiles = new HashSet<File>(rawScan());
		Set<File> intersection = new HashSet<File>(newFiles); 
		Map<File, Material> map = bindMaterialToFile(pool);
		Set<File> lostFiles = map.keySet();
		
		intersection.retainAll(lostFiles);
		newFiles.removeAll(intersection);
		lostFiles.removeAll(intersection);
		
		setMaterialsToLost(map.values());
		pool.addMaterials(this.convertToMaterials(newFiles));
	}
	
	private Map<File, Material> bindMaterialToFile(MaterialPool pool) {
		Map<File, Material> map = new HashMap<File, Material>(pool.size());
		for (Material material: pool) {
			map.put(material.getUnderlyingFile(), material);
		}
		
		return map;
	}
	
	private void setMaterialsToLost(Collection<Material> materials) {
		for (Material material: materials) {
			material.setLost(true);
		}
	}
			
}
