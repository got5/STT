package net.atos.survey.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7878477100060259226L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name="QUESTION_REF")
	protected Question question;
	
	@Column(length=300)
	private String answer;

	
	
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(Question question) {
		super();
		this.question = question;
	}

	public long getId() {
		return id;
	}

	
	
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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
		Response other = (Response) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Question getQuestion() {
		return question;
	}

}
