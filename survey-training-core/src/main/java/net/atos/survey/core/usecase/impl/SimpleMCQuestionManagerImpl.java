package net.atos.survey.core.usecase.impl;

import static net.atos.survey.core.tool.Param.CAT_DEFAULT;
import static net.atos.survey.core.tool.Param.MCQ1;
import static net.atos.survey.core.tool.Param.MCQ2;
import static net.atos.survey.core.tool.Param.TF;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.dao.SimpleMCQuestionDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.usecase.SimpleMCQuestionManager;

@Stateless(name="net.atos.survey.core.usecase.SimpleMCQuestionManager")
public class SimpleMCQuestionManagerImpl implements SimpleMCQuestionManager{
	
	@EJB SimpleMCQuestionDao simpleMCQuestionDao;
	@EJB CategoryDao categoryDao;

	
	private SimpleMCQuestion createMCQuestion(String q)
			throws NotInitaliazedSurveyDataBaseException {
		SimpleMCQuestion question = simpleMCQuestionDao.findByName(q);
		Category category=categoryDao.findByName(CAT_DEFAULT);
		
		if(question == null || category == null)
			throw new NotInitaliazedSurveyDataBaseException(q+" doen't exist yet. Please init your database");
		
		question = question.clone();
		question.setCategory(category);
		
		question = simpleMCQuestionDao.save(question);
		
		return question;
	}

	@Override
	public SimpleMCQuestion createMCQ1Question()
			throws NotInitaliazedSurveyDataBaseException {
		
		
		return createMCQuestion(MCQ1);
	}
	
	@Override
	public SimpleMCQuestion createMCQ2Question()
			throws NotInitaliazedSurveyDataBaseException {
		return createMCQuestion(MCQ2);
	}

	@Override
	public SimpleMCQuestion createTFQuestion()
			throws NotInitaliazedSurveyDataBaseException {
		return createMCQuestion(TF);
	}

	@Override
	public SimpleMCQuestion updateQuestion(SimpleMCQuestion question) {
		
		return simpleMCQuestionDao.update(question);
	}

}