package net.atos.survey.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Table@Entity
public class SimpleMCQResponse extends Response{
	
	
	@ManyToMany
	private List<Choice> choices = new ArrayList<Choice>();

	public SimpleMCQResponse(Question question){
		super(question);
	}
	
	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	
	
	

	

	
	
	
	
	
	
	
	

}
