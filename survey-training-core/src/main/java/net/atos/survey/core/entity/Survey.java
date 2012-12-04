package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table@Entity
public class Survey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2582069950464526420L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private boolean publish = false;
	
	private int version = 0;
	
	private String name;
	
	@ManyToOne
	private Training training;
	
	
	
	@OneToMany
	private List<Category> categories = new ArrayList<Category>();
	
	
	
	public Survey(String name,Training training) {
		super();
		this.training = training;
	}



	public long getId() {
		return id;
	}
	
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	public void publier(){
		publish = true;
	}
	
	


	public int getVersion() {
		return version;
	}



	public void incVersion() {
		this.version++;
	}



	public Training getTraining() {
		return training;
	}




	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<Category> getCategories() {
		return categories;
	}



	public void setCategory(List<Category> categories) {
		this.categories = categories;
	}


	
	public void addCategory(Category category) {
		if(!categories.contains(category)){
			
			
			categories.add(category);
		}	
	}
	
	public void deleteCategory(Category category){
		
			
		int index = categories.indexOf(category);
		if(index!=-1)
			categories.remove(index);
	}
	
	public void loadCategories(){
		this.categories.size();
	}
	

}
