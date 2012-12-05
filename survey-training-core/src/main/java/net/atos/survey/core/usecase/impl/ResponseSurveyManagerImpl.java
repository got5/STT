package net.atos.survey.core.usecase.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.usecase.ResponseSurveyManager;

@Stateless(name="net.atos.survey.core.usecase.ResponseSurveyManager")
public class ResponseSurveyManagerImpl implements ResponseSurveyManager{
	
	@EJB
	ResponseSurveyDao responseSurveyDao;

	@Override
	public ResponseSurvey save(ResponseSurvey responseSurvey) {
		return responseSurveyDao.save(responseSurvey);
	}

}
