package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.SimpleMCQuestion;





@Local
public interface SimpleMCQuestionDao extends Dao<Long, SimpleMCQuestion> {

	SimpleMCQuestion findByName(String name);
		
	
}
