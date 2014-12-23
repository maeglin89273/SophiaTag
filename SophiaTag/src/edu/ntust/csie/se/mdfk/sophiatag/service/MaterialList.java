package edu.ntust.csie.se.mdfk.sophiatag.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.ntust.csie.se.mdfk.sophiatag.data.Material;
import edu.ntust.csie.se.mdfk.sophiatag.data.MaterialTagger;
import edu.ntust.csie.se.mdfk.sophiatag.data.MaterialTagger.MaterialDiscardedListener;
import edu.ntust.csie.se.mdfk.sophiatag.data.UniqueList;


public class MaterialList implements MaterialDiscardedListener, Iterable<Material>, Serializable {
	
	private List<Material> materials;
	private transient MaterialList selection = null;
	private transient List<MaterialRemovedListener> listeners;
	
	MaterialList() {
		this(new UniqueList<Material>());
	}
	
	MaterialList(Collection<Material> materials) {
		this.materials = new UniqueList<Material>(materials);
		restoreInit();
	}
	
	void restoreInit() {
		this.listeners = new ArrayList<MaterialRemovedListener>();
		MaterialTagger.getInstance().addMaterialDiscardedListener(this);
	}
	
	public void addMaterial(Material material) {
		this.materials.add(material);
	}
	
	public void addMaterials(Collection<Material> materials) {
		this.materials.addAll(materials);
	}
	
	public boolean hasMaterials() {
		return !this.materials.isEmpty();	
	}
	
	public Material get(int index) {
		return this.materials.get(index);
	}
	
	public int indexOf(Material material) {
		return this.materials.indexOf(material);
	}
	
	public int size() {
		return this.materials.size();
	}
	
	public void setList(Collection<Material> newMaterials) {
		this.materials.clear();
		this.addMaterials(newMaterials);
	}
	
	public void onDiscarded(Material material) {
		
		int index = materials.indexOf(material);
		materials.remove(material);
		
		this.notifyMaterialRemovedListener(material, index);
	}
	
	private void notifyMaterialRemovedListener(Material material, int index) {
		for (int i = listeners.size() - 1; i >= 0; i--)  {
			listeners.get(i).onRemoved(material, index);
		}
		
	}
	
	public MaterialList select(Collection<Material> selections) {
		if (this.selection != null) {
			MaterialTagger.getInstance().removeMaterialDiscardedListener(this.selection);
			this.selection.listeners.clear();
		}
		this.selection = new Selection(selections, materials);
		
		return this.selection;
		
	}
	
	@Override
	public Iterator<Material> iterator() {
		return this.materials.iterator();
	}
	
	public void addMaterialRemovedListener(MaterialRemovedListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeMaterialRemovedListener(MaterialRemovedListener listener) {
		this.listeners.remove(listener);
	}
	
	
	public interface MaterialRemovedListener {
		public abstract void onRemoved(Material material, int indexInList);
	}
	
	private class Selection extends MaterialList {
		
		private Selection(Collection<Material> selections, List<Material> parent) {
			selections.retainAll(parent);
			this.addMaterials(selections);
		}
		
		@Override
		public void addMaterial(Material material) {
			MaterialList.this.addMaterial(material);
			super.addMaterial(material);
		}
		
		@Override
		public void addMaterials(Collection<Material> materials) {
			MaterialList.this.addMaterials(materials);
			super.addMaterials(materials);
		}
		
	}
	
}

