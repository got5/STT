package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;


@Local
public interface ResponseSurveyManager {

	ResponseSurvey createResponseSurvey(Survey survey);
	
	

}
