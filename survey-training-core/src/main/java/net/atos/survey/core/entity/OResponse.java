package net.atos.survey.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class OResponse extends Response {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3385376762085197632L;

	public OResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OResponse(Question question) {
		super(question);

	}

	

	

}
