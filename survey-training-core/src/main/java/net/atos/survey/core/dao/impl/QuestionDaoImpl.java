package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.QuestionDao;
import net.atos.survey.core.entity.Question;

@Stateless(name="net.atos.survey.core.dao.QuestionDao")
public class QuestionDaoImpl extends DaoImpl<Long, Question> implements QuestionDao {

	
	
	
}
