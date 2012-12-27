package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.usecase.InitManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;



public class Init {
	

	@Inject
	InitManager initManager;
	
	@OnEvent(value = EventConstants.ACTION, component = "initdb")
	public void clickOnForward() {

		initManager.initDB();
		try {
			initManager.testOneSurvey();
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TrainingNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotInitaliazedSurveyDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RoomNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}