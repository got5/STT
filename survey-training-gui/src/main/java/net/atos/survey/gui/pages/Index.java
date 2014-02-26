/**
 * 
 */
package net.atos.survey.gui.pages;

import javax.inject.Inject;

import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;

public class Index {

	@SessionState
	@Property
	private User loggedUser;

	@Property
	private boolean loggedUserExists;

	@Property
	@Validate(value = "required")
	private String login;

	@Property
	@Validate(value = "required")
	private String password;

	@Component(id = "loginForm")
	private Form loginForm;

	@Inject
	private Messages messages;

	@Inject
	private UserManager userManager;

	@Property
	@Persist
	private int count;

	@Component
	private Zone myZone;

	@OnEvent(value = EventConstants.ACTIVATE)
	public void activate() {
		loggedUser = null;

	}

    public boolean getDbNotLoaded(){
        if (userManager.countUser() != 0) {
            return false;
        }
        return true;
    }

	@OnEvent(value = EventConstants.VALIDATE, component = "loginForm")
	public void validation() {

		if (!userManager.checkLoginPassword(login, password)) {
			String errorMessage = "Wrong login or password";
			if (messages.contains("wrong-login-password"))
				errorMessage = messages.get("wrong-login-password");
			loginForm.recordError(errorMessage);
		}
	}

	/*
	 * This method is only called if you've NOT used the "recordError" method
	 * exposed by the Form component
	 */
	@OnEvent(value = EventConstants.SUCCESS, component = "loginForm")
	public Object loggingSuccess() {
		loggedUser = userManager.getUserByLogin(login);
		count = 0;
		return MyTrainings.class;
	}

	/*
	 * This method is only called if you've used the "recordError" method
	 * exposed by the Form component
	 */
	@OnEvent(value = EventConstants.FAILURE, component = "loginForm")
	public void loggingFailure() {

		count++;
	}

	/*
	 * This method is always called
	 */
	@OnEvent(value = EventConstants.SUBMIT, component = "loginForm")
	public Object loggingSubmit() {
		if (getMaxAttemptsExceeded()) {
			loginForm.clearErrors();
			if (messages.contains("limit-exceeded")) {
				loginForm.recordError(messages.get("limit-exceeded"));
			} else {
				loginForm.recordError("You have failed 3 times.");
			}
		}
		return myZone.getBody();
	}

	private boolean getMaxAttemptsExceeded() {
		return count >= 3;
	}

}