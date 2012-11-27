/**
 * 
 */
package net.atos.survey.gui.pages;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.InitManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index {
	
	
	@Inject InitManager initManager;
	@Inject UserDao userDao;
	
	
	
	@OnEvent(EventConstants.ACTIVATE)
	public void creerFrancois(){
		
		User fr = new User("Facon", "Francois", "Seclin", "GPS", "RD");
		
		fr = userDao.save(fr);
		
		initManager.initDB();
		
		}
	
	

}