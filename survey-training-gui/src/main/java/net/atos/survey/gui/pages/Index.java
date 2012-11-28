/**
 * 
 */
package net.atos.survey.gui.pages;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.usecase.InitManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index {
	
	
	@Inject InitManager initManager;
	
	
	
	@OnEvent(EventConstants.ACTIVATE)
	public void creerFrancois(){
		
		
		
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