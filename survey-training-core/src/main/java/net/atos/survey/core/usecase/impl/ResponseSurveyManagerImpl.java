package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.usecase.ResponseSurveyManager;

@Stateless(name="net.atos.survey.core.usecase.ResponseSurveyManager")
public class ResponseSurveyManagerImpl implements ResponseSurveyManager{
	
	@Inject
	ResponseSurveyDao responseSurveyDao;

	@Override
	public ResponseSurvey save(ResponseSurvey responseSurvey) {
		return responseSurveyDao.save(responseSurvey);
	}

}
