package net.atos.survey.gui.components.admin;

import java.text.SimpleDateFormat;

import net.atos.survey.core.entity.TrainingSession;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class TrainingSessionComponent {
	
	@Inject
	private Messages messages;
	
		
	@Property
	@Parameter
	private TrainingSession trainingSession;
	
	public int getNumberParticipants(){
		return trainingSession.loadUsers();
	}
	public int getNumberResponses(){
		return trainingSession.loadResponses();
	}
	
	
	

}
