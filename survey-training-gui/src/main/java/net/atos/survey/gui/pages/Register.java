package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Register {
	
	@SessionState
	private User loggedUser;
	
	@Inject
	UserManager userManager;
	@Inject
	TrainingSessionManager trainingSessionManager;

	@Property
	@Validate(value = "required")
	private String name;
	@Property
	@Validate(value = "required")
	private String firstName;
	
	@Property
	@Validate(value = "required")
	private String entity;
	
	@Property
	@Validate(value = "required")
	private String bu;
	
	@Property
	@Validate(value = "required")
	private String dept;
	
	@Property
	@Validate(value = "required")
	private String function;
	
	@InjectPage MyTrainings mytrainings;
	
	@Component
	private Form registerForm;
	
	

	@OnEvent(value = EventConstants.SUCCESS, component = "registerForm")
	public Object logginSuccess() {
		User newUser = new User(name, firstName, entity, bu, dept, function);
		newUser = userManager.register(newUser);
		
		trainingSessionManager.applyForTodayTapestrySession(newUser);
		
		loggedUser=newUser;
		mytrainings.setStatusFromRegister();
		return mytrainings;
		
	}

	
	
	
	
	
	
}
