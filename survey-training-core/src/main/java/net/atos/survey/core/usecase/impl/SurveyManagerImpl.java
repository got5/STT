package net.atos.survey.core.usecase.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.SurveyDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.SurveyTemplate;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.usecase.SurveyManager;

@Stateless(name="net.atos.survey.core.usecase.SurveyManager")
public class SurveyManagerImpl implements SurveyManager{

	@EJB SurveyDao surveyDao;
	@EJB TrainingDao trainingDao;
	
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
		survey.loadQuestions();
		return survey;
		
	}
	
	

}
