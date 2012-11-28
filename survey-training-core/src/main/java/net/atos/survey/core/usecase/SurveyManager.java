package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.SurveyTemplate;
import net.atos.survey.core.exception.TrainingNotExistException;


@Local
public interface SurveyManager{

	Survey createSurvey(String name,Long trainingId) throws TrainingNotExistException;
	
	Survey createSurveyFromTemplate(Long trainingId,SurveyTemplate template);
	
	Survey updateSurvey(Survey survey);
	
	
}
