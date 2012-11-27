package net.atos.survey.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity@Table
public class OQuestion extends Question {
	
	
	

	public OQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OQuestion(String title) {
		super();
		this.title=title;
		
	}




	
	

	
	
}
