package net.atos.survey.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table@Entity
public class OResponse extends Response {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1593189140443365958L;
	private String answer;

	public OResponse(Question question,String answer) {
		super(question);
		this.answer = answer;
	}
	
	public OResponse(Question question) {
		super(question);
		
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	
	

}
