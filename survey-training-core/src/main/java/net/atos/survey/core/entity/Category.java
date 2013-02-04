package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8241322362901355084L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false)
	private String name;

	@OneToMany(fetch=FetchType.EAGER)
	@OrderColumn
	private List<Question> questions = new ArrayList<Question>();
	
	private String remarque;
	
	

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(String name) {
		super();
		this.name = name;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addQuestion(Question question) {
		if (!questions.contains(question)) {

			questions.add(question);
		}
	}

	public void deleteQuestion(Question question) {

		int index = questions.indexOf(question);
		if (index != -1)
			questions.remove(index);
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void loadQuestions() {
		questions.size();

	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	
	

}
