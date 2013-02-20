package net.atos.survey.gui.components.admin;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;
import net.atos.survey.gui.components.SurveyForm;
import net.atos.survey.gui.pages.admin.PDFPage;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import javax.inject.Inject;

public class TraineeComponent {

	@Inject
	private SurveyManager surveyManager;

	@Inject
	private ResponseSurveyManager responseSurveyManager;

	@Inject
	private TrainingSessionManager trainingSessionManager;

	@Inject
	UserManager userManager;

	private TrainingSession ts;
	
	@Property
	private User trainee;

	@Property
	private ResponseSurvey responseSurvey;

	@Property
	private Survey survey;

	@Property
	@Parameter
	private Long traineeId;

	@Property
	@Parameter
	private Long trainingSessionId;

	@InjectComponent
	private SurveyForm surveyForm;

	@InjectPage
	private PDFPage pdfPage;

	@SetupRender
	public void applyForActivate() {
		
		ts = trainingSessionManager.findById(trainingSessionId);
		
		if (traineeId != null || trainingSessionId != null) {
			responseSurvey = responseSurveyManager
					.findByTrainingSessionAndByTrainee(trainingSessionId,
							traineeId);
		}

		if (isResponses()) {
			survey = surveyManager.findByTrainingSession(trainingSessionId);
			trainee = userManager.findById(traineeId);
		}

	}
	
	
	

	@OnEvent(EventConstants.SUCCESS)
	public void applyForSuccess() {
		// do nothing because the form has to be disabled
		// from surveyevent
	}

	@AfterRender
	public void afterRender() {

	}

	public boolean isResponses() {
		return responseSurvey != null;
	}

	public boolean isInFuture() {

		boolean ret = false;
		Calendar now = new GregorianCalendar();
		Calendar from = ts.getDateS();
		if (from.getTimeInMillis() > now.getTimeInMillis()
				&& from.get(Calendar.DAY_OF_MONTH) != now
						.get(Calendar.DAY_OF_MONTH)) {
			ret = true;
		}
		return ret;
	}

	@OnEvent(value = EventConstants.ACTION, component = "print")
	public Object makePdf() {
		pdfPage.configureDocument(trainingSessionId, traineeId);
		return pdfPage;

	}

	@OnEvent(value = EventConstants.ACTION, component = "delete")
	public void deleteTrainee() {
		trainingSessionManager.removeTrainee(trainingSessionId, traineeId);

	}

}
