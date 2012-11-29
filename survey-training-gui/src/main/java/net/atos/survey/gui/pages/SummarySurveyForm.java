package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.gui.components.SurveyForm;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;


public class SummarySurveyForm {
	
	@Inject
	TrainingSessionManager trainingSessionManager;
	
	@Inject 
	SurveyManager surveyManager;
	
	@Property
	private TrainingSession trainingSession;
	
	@InjectComponent
	private SurveyForm surveyForm;
	
	@OnEvent(EventConstants.ACTIVATE)
	public void loadingForm(Long trainingSessionId){
		
		trainingSession = trainingSessionManager.findById(trainingSessionId);
		
		Survey survey = surveyManager.loadAll(trainingSession.getSurvey());
		
		surveyForm.setSurvey(survey);
		
		
	}
	
	@OnEvent(EventConstants.PASSIVATE)
	public Long passivateForm(){
		return trainingSession.getId();
	}

}
