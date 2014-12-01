package edu.ntust.csie.se.mdfk.sophiatag.service;
import java.util.LinkedList;

import edu.ntust.csie.se.mdfk.sophiatag.data.Material;
import edu.ntust.csie.se.mdfk.sophiatag.data.MaterialTagger;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

import edu.ntust.csie.se.mdfk.sophiatag.data.MaterialTagger.MaterialDiscardedListener;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class MaterialPool implements MaterialDiscardedListener, Iterable<Material>{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Set<Material> materials;
	

	/**
	 * constructor
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public MaterialPool(Collection<Material> materials) {
			this.addMaterials(materials);
			MaterialTagger.getInstance().addMaterialDiscardedListener(this);
	}
	
	
	public void addMaterial(Material material) {
		this.materials.add(material);
	}
	
	public void addMaterials(Collection<Material> materials) {
		this.materials.addAll(materials);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean hasMaterials() {
		return !this.materials.isEmpty();	
	}
	
	public int size() {
		return this.materials.size();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void onDiscarded(Material material) {
		materials.remove(material);
	}



	@Override
	public Iterator<Material> iterator() {
		return Collections.unmodifiableSet(this.materials).iterator();
	}
	
}
