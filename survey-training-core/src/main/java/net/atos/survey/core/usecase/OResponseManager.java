package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.OResponse;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;


@Local
public interface OResponseManager {
	
	OResponse save(OResponse oResponse);
	
	
	

}
