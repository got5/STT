package net.atos.survey.gui.components.admin;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.usecase.TrainingSessionManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import javax.inject.Inject;

public class TrainingSessionComponent {
	
	@Inject
	private Messages messages;
	
	private TrainingSession ts;
	
	@Inject TrainingSessionManager trainingSessionManager;
	
	@SetupRender
	public void load(){
		ts = trainingSessionManager.findById(trainingSessionId);
	}
		
	@Property
	@Parameter
	private Long trainingSessionId;
	
	public boolean isInFuture(){
		
		boolean ret = false;
		Calendar now = new GregorianCalendar();
		Calendar from = ts.getDateS();
		if(from.getTimeInMillis() > now.getTimeInMillis() && from.get(Calendar.DAY_OF_MONTH)!=now.get(Calendar.DAY_OF_MONTH)){
			ret = true;
		}
		return ret;
	}
	
	

	@OnEvent(value=EventConstants.ACTION,component="delete")
	public void deleteTrainingSession(){
		trainingSessionManager.delete(trainingSessionId);
		
	}
	
	

}
