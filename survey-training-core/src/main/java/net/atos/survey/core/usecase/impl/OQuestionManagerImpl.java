package net.atos.survey.core.usecase.impl;

import static net.atos.survey.core.tool.Param.CAT_DEFAULT;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.dao.OQuestionDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.usecase.OQuestionManager;

@Stateless(name = "net.atos.survey.core.usecase.OQuestionManager")
public class OQuestionManagerImpl implements OQuestionManager {

	@EJB
	OQuestionDao oQuestionDao;
	@EJB
	CategoryDao categoryDao;

	@Override
	public OQuestion createDefaultOQuestion()
			throws NotInitaliazedSurveyDataBaseException {
		Category category = categoryDao.findByName(CAT_DEFAULT);
		if (category == null)
			throw new NotInitaliazedSurveyDataBaseException(CAT_DEFAULT
					+ " doen't exist yet. Please init your database");
		OQuestion oq = new OQuestion();
		
		return oQuestionDao.save(oq);
	}

	@Override
	public OQuestion updateQuestion(OQuestion question) {
		return oQuestionDao.update(question);
	}

}
