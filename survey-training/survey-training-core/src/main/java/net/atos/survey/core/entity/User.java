package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table@Entity
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String name;
	
	private String firstName;
	
	private String entity;
	
	private String bu;
	
	private String dept;
	
	@ManyToMany(mappedBy="inChargeUsers")
	private List<Training> inChargeOfTrainings = new ArrayList<Training>();
	

	public void addInChargeOfTrainings(Training training){
		if(!inChargeOfTrainings.contains(training))
			inChargeOfTrainings.add(training);
	}
	

	public List<Training> getInChargeOfTrainings() {
		return inChargeOfTrainings;
	}





	




	public User(String name, String firstName, String entity, String bu,
			String dept) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.entity = entity;
		this.bu = bu;
		this.dept = dept;
	}
	
	


	public long getId() {
		return id;
	}


	

	public String getEntity() {
		return entity;
	}


	public String getBu() {
		return bu;
	}


	public String getDept() {
		return dept;
	}


	public String getName() {
		return name;
	}



	public String getFirstName() {
		return firstName;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
}