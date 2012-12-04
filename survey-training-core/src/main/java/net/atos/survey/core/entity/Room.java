package net.atos.survey.core.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table@Entity
public class Room implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8008503619290696220L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String name;
	
	
	
	
	
	
	public Room() {
		super();
		
	}
	



	public Room(String name) {
		super();
		this.name = name;
		
	}





	public long getId() {
		return id;
	}



	public String getName() {
		return name;
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
		Room other = (Room) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
	