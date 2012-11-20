package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table@Entity
public class Training implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private TypeTraining type;
	
	@OneToMany(mappedBy="training")
    private List<TrainingSession> sessions = new ArrayList<TrainingSession>();
	
	@ManyToMany
	private List<User> inChargeUsers = new ArrayList<User>();
	

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void addUserInCharge(User user){
		if(!inChargeUsers.contains(user))
			inChargeUsers.add(user);
	}

	public List<User> getInChargeUsers() {
		return inChargeUsers;
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
		Training other = (Training) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addTrainingSession(TrainingSession tS){
		if(!sessions.contains(tS))
				
				sessions.add(tS);
	}
	
	public List<TrainingSession> getTrainingSessions(){
		return sessions;
	}
	
	
}