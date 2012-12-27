package net.atos.survey.core.entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Table
@Entity
public class SimpleMCQuestion extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8374850305291884636L;

	private boolean uniqueAnswer = true;

	private String elseClause;

	@ManyToOne
	private Choice trigger;

	private boolean triggerOrLess = false;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "MCQ_CHOICE")
	@MapKeyJoinColumn(name = "CHOICE_ID")
	@Column(name = "NUMERO")
	@OrderColumn(name = "NUMERO")
	private Map<Choice, Integer> choices = new LinkedHashMap<Choice, Integer>();

	public SimpleMCQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleMCQuestion(String name, boolean uniqueAnswer,
			String elseClause, boolean triggerOrLess) {
		super();
		this.name = name;
		this.uniqueAnswer = uniqueAnswer;
		this.elseClause = elseClause;
		this.triggerOrLess = triggerOrLess;
	}

	private int getChoicesSize() {
		return choices.size();
	}

	public void addChoice(Choice choice) {
		Set<Choice> keys = choices.keySet();
		if (!keys.contains(choice))
			choices.put(choice, getChoicesSize());
	}

	public Collection<Choice> getChoices() {
		return choices.keySet();
	}

	public void deleteLastChoice(Choice choice) {
		if (choices.containsKey(choice)) {
			if (choices.get(choice) == getChoicesSize() - 1) // On s'assure
																// qu'on retire
																// bien le
																// dernier
																// élément
				choices.remove(choice);
		}
	}

	public boolean isUniqueAnswer() {
		return uniqueAnswer;
	}

	public void setUniqueAnswer(boolean uniqueAnswer) {
		this.uniqueAnswer = uniqueAnswer;
	}

	public Choice getTrigger() {
		return trigger;
	}

	public void setTrigger(Choice trigger) {
		this.trigger = trigger;
	}

	public SimpleMCQuestion clone() {

		SimpleMCQuestion question = new SimpleMCQuestion(null,
				this.uniqueAnswer, this.elseClause, this.triggerOrLess);
		question.trigger = this.trigger;

		for (Choice k : this.choices.keySet())
			question.choices.put(k, this.choices.get(k));

		return question;
	}

	public String getElseClause() {
		return elseClause;
	}

	public void setElseClause(String elseClause) {
		this.elseClause = elseClause;
	}

	public boolean isTriggerOrLess() {
		return triggerOrLess;
	}

	public void setTriggerOrLess(boolean triggerOrLess) {
		this.triggerOrLess = triggerOrLess;
	}

	public void loadChoices() {
		choices.size();

	}

	public boolean isTrigger(Choice choice) {
		boolean ret = false;
		if (elseClause != null) {
			if (isTriggerOrLess()) {
				if (choices.get(choice) >= choices.get(trigger))
					ret = true;
			} else {
				if (choices.get(choice) == choices.get(trigger))
					ret = true;

			}
		}
		return ret;
	}

}
