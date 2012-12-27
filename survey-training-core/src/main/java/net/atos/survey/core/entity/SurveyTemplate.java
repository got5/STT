package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table
@Entity
public class SurveyTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8223808575959159628L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToMany
	private List<Question> questions = new ArrayList<Question>();
	
	

	public SurveyTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void addQuestion(Question question) {
		if (!questions.contains(question))
			questions.add(question);

	}

	public void deleteQuestion(Question question) {
		int index = questions.indexOf(question);
		if (index != -1)
			questions.remove(index);
	}

	public List<Question> getQuestions() {
		return Collections.unmodifiableList(questions);
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
		SurveyTemplate other = (SurveyTemplate) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
