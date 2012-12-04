package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;


@Local
public interface SimpleMCQuestionManager {
	
	SimpleMCQuestion createMCQ1Question() throws NotInitaliazedSurveyDataBaseException;
	
	SimpleMCQuestion createMCQ2Question() throws NotInitaliazedSurveyDataBaseException;
	
	SimpleMCQuestion createTFQuestion() throws NotInitaliazedSurveyDataBaseException;
	

	SimpleMCQuestion updateQuestion(SimpleMCQuestion question);
	
	SimpleMCQuestion loadAll(SimpleMCQuestion simpleMCQuestion);
	
	

}
