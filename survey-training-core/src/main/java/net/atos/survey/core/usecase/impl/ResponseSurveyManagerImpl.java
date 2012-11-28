package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.usecase.ResponseSurveyManager;

@Stateless(name="net.atos.survey.core.usecase.ResponseSurveyManager")
public class ResponseSurveyManagerImpl implements ResponseSurveyManager{

	@Override
	public ResponseSurvey createResponseSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

}
