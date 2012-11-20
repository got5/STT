/**
 * 
 */
package net.atos.survey.gui.pages;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index {
	
	
	@Inject UserDao usagerDao;
	
	
	@OnEvent(EventConstants.ACTIVATE)
	public void creerFrancois(){
		
		
		User usager = new User("Facon", "Francois", "Seclin", "GPS", "DO");

		usager = usagerDao.enregistrer(usager);
		
		
	}
	
	

}