package net.atos.survey.gui.components.admin;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SurveyManager;
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
import org.apache.tapestry5.ioc.annotations.Inject;

public class TraineeComponent {

	@Inject
	private SurveyManager surveyManager;

	@Inject
	private ResponseSurveyManager responseSurveyManager;
	
	@Inject UserManager userManager;

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
		if(traineeId!=null || trainingSessionId!=null){
			responseSurvey = responseSurveyManager.findByTrainingSessionAndByTrainee(trainingSessionId, traineeId);
		}
		
		if(isResponses()){
			survey = surveyManager.findByTrainingSession(trainingSessionId);
			trainee = userManager.findById(traineeId);
		}
		
		
	}
	
	@OnEvent(EventConstants.SUCCESS)
	public void applyForSuccess() {
		//do nothing because the form has to be disabled
		//from surveyevent
	}

	
	@AfterRender
	public void afterRender(){
		
	}
	
	public boolean isResponses(){
		return responseSurvey!=null;
	}
	
	@OnEvent(value=EventConstants.ACTION,component="print")
	public Object makePdf(){
		pdfPage.configureDocument(trainingSessionId, traineeId);
		return pdfPage;
		
	}

}
