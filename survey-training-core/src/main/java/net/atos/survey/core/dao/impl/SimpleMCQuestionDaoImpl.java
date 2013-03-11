package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.SimpleMCQuestionDao;
import net.atos.survey.core.entity.SimpleMCQuestion;

@Stateless
public class SimpleMCQuestionDaoImpl extends DaoImpl<Long, SimpleMCQuestion> implements SimpleMCQuestionDao {

	
	public SimpleMCQuestion findByName(String name){
		
		return find("select s from SimpleMCQuestion s "+
				"where s.name=?1", name);
	}
	
}
