package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.gui.components.SurveyForm;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library="context:static/js/question-component.js")
public class SummarySurveyForm {
	
	@Inject
	private JavaScriptSupport jss;

	@SessionState
	private User user;

	@Inject
	private TrainingSessionManager trainingSessionManager;

	@Property
	@Persist
	private ResponseSurvey responseSurvey;

	@Inject
	SurveyManager surveyManager;

	@Property
	private TrainingSession trainingSession;

	@Component
	private SurveyForm surveyForm;
	
	@Property 
	private Survey survey;

	@OnEvent(EventConstants.ACTIVATE)
	public void loadingForm(Long trainingSessionId) {

		trainingSession = trainingSessionManager.findById(trainingSessionId);

		survey = surveyManager.loadAll(trainingSession.getSurvey());
		if (responseSurvey == null) {
			responseSurvey = trainingSessionManager
					.createResponseSurveyWithoutPersist(trainingSession);
		}

	}

	@OnEvent(EventConstants.PASSIVATE)
	public Long passivateForm() {
		return trainingSession.getId();
	}

	@AfterRender
	public void loadingScript(){
		jss.addScript("loading();");
	}

}
