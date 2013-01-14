package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.ResponseSurveyManager;

@Stateless(name="net.atos.survey.core.usecase.ResponseSurveyManager")
public class ResponseSurveyManagerImpl implements ResponseSurveyManager{
	
	@Inject
	ResponseSurveyDao responseSurveyDao;
	@Inject
	TrainingSessionDao trainingSessionDao;
	@Inject
	UserDao userDao;

	@Override
	public ResponseSurvey save(ResponseSurvey responseSurvey) {
		return responseSurveyDao.save(responseSurvey);
	}

	@Override
	public ResponseSurvey findByTrainingSessionAndByTrainee(Long trainingSessionId, Long traineeId) {
		TrainingSession ts = trainingSessionDao.findById(trainingSessionId);
		User trainee = userDao.findById(traineeId);
		
		return responseSurveyDao.findByTrainingSessionAndByTrainee(ts,trainee);
	}

}
