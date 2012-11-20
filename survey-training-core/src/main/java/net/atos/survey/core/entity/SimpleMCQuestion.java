package net.atos.survey.core.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Table@Entity
public class SimpleMCQuestion extends Question {
	
	
	
	private boolean uniqueAnswer=true;
	
	private int triggerElse=0;
	
	
	@ElementCollection
	@CollectionTable(name="MCQ_CHOICE")
	@MapKeyJoinColumn(name="CHOICE_ID")
	@Column(name="NUMERO")
	@OrderColumn(name="NUMERO")
	private Map<Choice,Integer> choices = new HashMap<Choice,Integer>();

	private int getChoicesSize(){
		return choices.size();
	}
	
	public void addChoice(Choice choice){
		Set<Choice> keys = choices.keySet();
		if(!keys.contains(choice))
			choices.put(choice, getChoicesSize());
	}
	
	public Collection<Choice> getChoices(){
		return Collections.unmodifiableCollection(choices.keySet());
	}
	

	public void deleteLastChoice(Choice choice){
		if(choices.containsKey(choice)){
			if(choices.get(choice)==getChoicesSize()-1) //On s'assure qu'on retire bien le dernier élément
				choices.remove(choice);
		}
	}

	public boolean isUniqueAnswer() {
		return uniqueAnswer;
	}

	public void setUniqueAnswer(boolean uniqueAnswer) {
		this.uniqueAnswer = uniqueAnswer;
	}

	

	

	public int getTrigger() {
		return triggerElse;
	}

	public void setTrigger(int trigger) {
		this.triggerElse = trigger;
	}
	
	
	
	
	

}
