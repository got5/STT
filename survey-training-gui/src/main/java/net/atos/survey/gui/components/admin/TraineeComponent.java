package net.atos.survey.gui.components.admin;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SurveyManager;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class TraineeComponent {

	@Inject
	private SurveyManager surveyManager;
	
	@Inject
	private ResponseSurveyManager responseSurveyManager;
	
	
	
	@Property
	@Parameter
	private User trainee;
	
	@Property
	@Parameter
	private TrainingSession trainingSession;
	
	
	public ResponseSurvey getResponseSurvey(){
		return responseSurveyManager.findByTrainingSessionAndByTrainee(trainingSession.getId(),trainee.getId());
	}
	
}
