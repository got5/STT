package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;


@Local
public interface OQuestionManager {
	
	OQuestion createDefaultOQuestion() throws NotInitaliazedSurveyDataBaseException;
	
	OQuestion updateQuestion(OQuestion question);
	

	
	
	

}
