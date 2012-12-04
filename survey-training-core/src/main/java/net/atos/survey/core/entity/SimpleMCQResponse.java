package net.atos.survey.core.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class SimpleMCQResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3543551957793901412L;
	@ManyToOne
	private Choice choice;

	public SimpleMCQResponse(Question question) {
		super(question);

	}

	public void setChoice(Choice choice) {
		this.choice = choice;
	}

	public Choice getChoice() {
		return choice;
	}

}
