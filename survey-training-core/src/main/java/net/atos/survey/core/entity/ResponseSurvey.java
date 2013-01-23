package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity
public class ResponseSurvey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440382429358094349L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany
	@MapKey(name="question")
	private Map<Question, Response> responses = new HashMap<Question, Response>();
	
	

	public ResponseSurvey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void addResponse(Response response) {
		Question key = response.getQuestion();
		responses.put(key, response);
	}

	public void setResponse(List<Response> responses) {
		Question q;
		for (Response r : responses) {
			q = r.getQuestion();
			this.responses.put(q, r);
		}
	}

	public Map<Question, Response> getResponses() {
		return responses;
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
		ResponseSurvey other = (ResponseSurvey) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
