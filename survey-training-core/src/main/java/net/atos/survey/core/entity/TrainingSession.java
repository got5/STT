package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table
@Entity
public class TrainingSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8661434591689190746L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.DATE)
	private Calendar dateS;

	@Temporal(TemporalType.DATE)
	private Calendar dateE;

	@ManyToOne
	private Training training;

	@ManyToOne
	@JoinColumn(name = "ROOM_ID")
	private Room room;

	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR_ID")
	private User instructor;

	@ManyToOne
	private Survey survey;

	@ManyToMany
	private List<User> trainees = new ArrayList<User>();

	@OneToMany
	private Map<User, ResponseSurvey> responses = new HashMap<User, ResponseSurvey>();

	public TrainingSession(Calendar dateS, Calendar dateE, Training training,
			Room room) {
		super();
		this.dateS = dateS;
		this.dateE = dateE;
		this.training = training;
		this.room = room;
	}

	public TrainingSession(Calendar dateS, Calendar dateE, Room room,
			User instructor, Survey survey, Training training) {
		super();
		this.dateS = dateS;
		this.dateE = dateE;
		this.room = room;
		this.instructor = instructor;
		this.survey = survey;
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
		TrainingSession other = (TrainingSession) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	public List<User> getTrainees() {
		return trainees;
	}

	public void addTrainee(User user) {
		if (!trainees.contains(user))
			trainees.add(user);
	}

	public void removeTrainee(User user) {
		trainees.remove(user);
	}

	public void addResponseSurvey(User user, ResponseSurvey rS) {
		responses.put(user, rS);
	}

	public List<ResponseSurvey> getResponseSurveys() {
		return (List<ResponseSurvey>) responses.values();
	}

	public ResponseSurvey getResponseSurvey(User user) {

		return responses.get(user);
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Calendar getDateS() {
		return dateS;
	}

	public void setDateS(Calendar dateS) {
		this.dateS = dateS;
	}

	public Calendar getDateE() {
		return dateE;
	}

	public void setDateE(Calendar dateE) {
		this.dateE = dateE;
	}

	public Training getTraining() {
		return training;
	}

	public int loadResponses() {
		return responses.size();

	}

	public int loadUsers() {
		return trainees.size();
	}

}
