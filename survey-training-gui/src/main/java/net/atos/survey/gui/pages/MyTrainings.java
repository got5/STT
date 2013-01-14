package net.atos.survey.gui.pages;

import java.text.SimpleDateFormat;
import java.util.List;

import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.TrainingSessionManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class MyTrainings {

		@Inject
		private TrainingSessionManager trainingSessionManager;
		
		@Property(write=false)
		private List<TrainingSession> trainingSessions;
		
		@Property
		private TrainingSession trainingSession;

		@Property
		@SessionState
		private User loggedUser;

		private boolean loggedUserExists;

		@Property
		private int index = 0;
		
		@Property
		private SimpleDateFormat dateFormat;
		
		@Inject
		private Messages messages;
		
		@Inject
	    private AlertManager manager;
		

		private String rowClass;
		
		private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
		

		@OnEvent(EventConstants.ACTIVATE)
		public Object loadingForm() {

			if (!loggedUserExists) {
				return Index.class;
			}
			try {
				trainingSessions = trainingSessionManager.findByTrainee(loggedUser.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return Index.class;
			}
						
			if (messages.contains("datePattern")) {
				try {
					dateFormat = new SimpleDateFormat(messages.get("datePattern"));
				} catch (Exception ex) {
					dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
				}
			} else {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}

			return null;
		}

		
		public String getRowClass() {
			if (index % 2 == 0) {
				return "tbl1";
			} else {
				return "tbl2";
			}
		}

		public void setRowClass(String rowClass) {
			this.rowClass = rowClass;
		}

		public boolean isNoTrainingSession(){
			return trainingSessions.size()==0;
		}
		
		public void setStatusFromSurvey(String status){
			if(status.equals("OK")){
				if(messages.contains("status-message-ok")){
					 manager.alert(Duration.SINGLE, Severity.INFO, messages.get("status-message-ok"));
				}
				
			}
			if(status.equals("ERROR")){
				if(messages.contains("status-message-error")){
					 manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("status-message-error"));
				}
				
			}
			if(status.equals("PERMISSION")){
				if(messages.contains("status-message-permission")){
					 manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("status-message-permission"));
				}
				
			}
				
		}
		
	}



