package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.OQuestionDao;
import net.atos.survey.core.entity.OQuestion;

@Stateless(name="net.atos.survey.core.dao.OQuestionDao")
public class OQuestionDaoImpl extends DaoImpl<Long, OQuestion> implements OQuestionDao {

	@Override
	public OQuestion findByName(String name) {
		return find("select o from OpenQuestion o where o.name = ?1",name);
	}

	
	
	
}
