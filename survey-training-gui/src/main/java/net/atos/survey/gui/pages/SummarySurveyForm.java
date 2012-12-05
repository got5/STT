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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "context:static/js/question-component.js")
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

	@OnEvent(EventConstants.ACTIVATE)
	public Object loadingForm(Long trainingSessionId) {

		if (!loggedUserExists) {
			return Login.class;
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

	/*
	 * @OnEvent(EventConstants.PREPARE) public void prepareForAction(){
	 * 
	 * for(Question q:responseSurvey.getResponses().keySet()){ if(q instanceof
	 * SimpleMCQuestion){ SimpleMCQuestion mcq = (SimpleMCQuestion)q;
	 * SimpleMCQResponse mcqr =
	 * (SimpleMCQResponse)responseSurvey.getResponses().get(q);
	 * 
	 * if(!mcq.isTrigger(mcqr.getChoice())) mcqr.setElseClause(null);
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * }
	 */

	@OnEvent(EventConstants.SUCCESS)
	public Object applyForSuccess() {
		this.trainingSession = trainingSessionManager.saveResultForTrainee(
				trainingSession.getId(), loggedUser.getId(), responseSurvey);
		return Index.class;
	}

	@AfterRender
	public void loadingScript() {
		jss.addScript("loading();");
	}

}
