package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.OQuestion;





@Local
public interface OQuestionDao extends Dao<Long, OQuestion> {

	OQuestion findByName(String name);
	
}
