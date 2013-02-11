package net.atos.survey.core.usecase.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.SurveyDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.SurveyTemplate;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.usecase.CategoryManager;
import net.atos.survey.core.usecase.SimpleMCQuestionManager;
import net.atos.survey.core.usecase.SurveyManager;

@Stateless(name="net.atos.survey.core.usecase.SurveyManager")
public class SurveyManagerImpl implements SurveyManager{

	@Inject SurveyDao surveyDao;
	@Inject TrainingDao trainingDao;
	@Inject CategoryManager categoryManager;
	@Inject SimpleMCQuestionManager simpleMCQuestionManager;
	
	@Override
	public Survey createSurvey(String name,Long trainingId) throws TrainingNotExistException {
		Training training = trainingDao.findById(trainingId);
		if(training == null) 
			throw new TrainingNotExistException();
		
		Survey survey = new Survey(name,training);
		return surveyDao.save(survey);
	}

	@Override
	public Survey createSurveyFromTemplate(Long trainingId,
			SurveyTemplate template) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey updateSurvey(Survey survey) {
		return surveyDao.update(survey);
	}

	@Override
	public Survey loadAll(Survey survey) {
		survey = surveyDao.findById(survey.getId());
		survey.loadCategories();
		return survey;
		
	}

	@Override
	public List<Question> getAllQuestion(Survey survey) {
		survey = surveyDao.findById(survey.getId());
		survey.loadCategories();
		List<Question> questions = new ArrayList<Question>();
		for(Category c:survey.getCategories()){
			c.loadQuestions();
			questions.addAll(c.getQuestions());
		}
		return questions;
	}

	@Override
	public Survey findByTrainingSession(Long trainingSessionId) {
		Survey s =  surveyDao.findByTrainingSession(trainingSessionId);
		loadAll(s);
		return s;
	}

	@Override
	public Survey loadAllR(Survey survey) {
		Survey surveyR = loadAll(survey);
		
		for(Category c: surveyR.getCategories()){
			c =categoryManager.loadAll(c);
			for(Question q:c.getQuestions()){
				if(q instanceof SimpleMCQuestion){
					q=	simpleMCQuestionManager.loadAll((SimpleMCQuestion)q);
				}
			}
			
		}
		return surveyR;
	}
	
	

}
