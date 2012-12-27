package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;
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
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"context:static/js/question-component.js","context:static/js/surveyForm.js"})
public class SummarySurveyForm {

	@Inject
	private SurveyManager surveyManager;

	@Inject
	private TrainingSessionManager trainingSessionManager;

	@Inject
	private JavaScriptSupport jss;

	@Property
	@SessionState
	private User loggedUser;

	private boolean loggedUserExists;

	@Property
	private TrainingSession trainingSession;

	@Property
	private Survey survey;

	@Property
	@Persist
	private ResponseSurvey responseSurvey;

	@Component
	private SurveyForm surveyForm;
	
	@Inject
	private Messages messages;

	@OnEvent(EventConstants.ACTIVATE)
	public Object loadingForm(Long trainingSessionId) {

		if (!loggedUserExists) {
			return Index.class;
		}

		trainingSession = trainingSessionManager.findById(trainingSessionId);

		try {
			if (trainingSessionManager.alreadyAnsweredToSurvey(
					trainingSessionId, loggedUser.getId()))
				return Index.class;
		} catch (UserNotInTrainingSessionException e) {
			return Index.class;
		}

		if (responseSurvey == null) {
			responseSurvey = trainingSessionManager
					.createResponseSurveyWithoutPersist(trainingSession);
		}
		
		survey = surveyManager.loadAll(trainingSession.getSurvey());

		return null;
	}

	@OnEvent(EventConstants.PASSIVATE)
	public Long passivateForm() {
		return trainingSession.getId();
	}

	

	@OnEvent(EventConstants.SUCCESS)
	public Object applyForSuccess() {
		this.trainingSession = trainingSessionManager.saveResultForTrainee(
				trainingSession.getId(), loggedUser.getId(), responseSurvey);
		return Index.class;
	}

	@AfterRender
	public void loadingScript() {
		String mcq_error_message = "Veuillez répondre à toutes les questions à choix multiples.";
		if(messages.contains("mcq-error")){
			mcq_error_message = messages.get("mcq-error");
			
		}
		jss.addScript("loading();");
		jss.addScript("checkForm('%s');",mcq_error_message);
	}

}
