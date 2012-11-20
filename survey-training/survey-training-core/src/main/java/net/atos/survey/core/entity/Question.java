package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Table@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Question implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar dateCreation= new GregorianCalendar();
	
	protected String title;

	private int numQ;

	@ManyToOne
	@JoinColumn(name="CATEGORY_ID")
	protected Category category;
	
	@ManyToOne
	@JoinColumn(name="THEME_ID")
	protected Theme theme;

	public long getId() {
		return id;
	}
	
	

	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public Theme getTheme() {
		return theme;
	}



	public void setTheme(Theme theme) {
		this.theme = theme;
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
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}





	public Calendar getDateCreation() {
		return dateCreation;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getNumQ() {
		return numQ;
	}



	public void setNumQ(int numQ) {
		this.numQ = numQ;
	}
	
	
	
	

}
