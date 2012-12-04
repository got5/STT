package net.atos.survey.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity@Table
public class OQuestion extends Question {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3923357049318966368L;

	public OQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OQuestion(String title) {
		super();
		this.title=title;
		
	}




	
	

	
	
}
