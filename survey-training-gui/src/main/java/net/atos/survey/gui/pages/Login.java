package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {

	/**
	 * Used to store a UserManager instance on the page instance
	 */
	@Inject
	private UserManager userManager;

	/**
	 * This variable is used to set a reference on the authenticated after
	 * verify process
	 */
	@SessionState
	private User loggedUser;

	/**
	 * Use to store the user from the login form
	 */
	@Property
	@Persist
	private User user;
	
	/** Used to store in session the number of validation error */
	@Property
	@Persist
	private int count;
	
	
	
	/**
	 * Used to have a reference on the form component
	 */
	@InjectComponent 
	private BeanEditForm verifyForm;
	
	@OnEvent(EventConstants.ACTIVATE)
	public void activateManager() {
		
		if(user==null)
			user = new User();
		
		if(getMaxAttemptsExceeded()) {
			verifyForm.clearErrors();
			verifyForm.recordError("Tu t'es plant� 3 fois");
			}
	}

	/**
	 * This method implements the login process. Verify if the user exists and
	 * if his password is correct.
	 * 
	 * @return
	 */
	 
	@OnEvent(EventConstants.SUCCESS) 
	public Object verifyUser() {
	
		if(user.getLogin()==null)
			verifyForm.recordError("Veuillez saisir votre login");
			
		if(user.getPassword()==null)
			verifyForm.recordError("Veuillez saisir votre mot-de-passe");
		
		if(verifyForm.isValid()){
			User tempUser= userManager.getUserByLogin(user.getLogin());
			if(tempUser!=null){
				if(tempUser.getPassword().equals(user.getPassword())){
				
				loggedUser=tempUser;
				return Index.class;
				}
			}else{
				loggedUser=null;
				count++;
				verifyForm.recordError("Erreur : Mauvais login/mot-de-passe");
				return null;
				}
			}
			loggedUser=null;
			count++;
			verifyForm.recordError("Erreur : Mauvais login/mot-de-passe");
			return null;
					
		
	}
	
	/**
	 * Used to verify if the login form must be disabled
	 * 
	 * @return true if the number of failed attempts exceeds 3, false otherwise
	 */
	public boolean getMaxAttemptsExceeded() {
		return count >= 3;
	}
}