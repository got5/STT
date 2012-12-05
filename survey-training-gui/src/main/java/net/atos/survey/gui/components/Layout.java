package net.atos.survey.gui.components;

import net.atos.survey.core.entity.User;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

@Import(stylesheet = {"context:static/css/k-structure.css", "context:static/css/k-theme1.css"})
public class Layout {
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String title;
	
	@SessionState
	private User loggedUser;

	@Property
	private Boolean loggedUserExists; 
	
	public String getTitle(){
		return title;
	}

	@OnEvent(value=EventConstants.ACTION, component="logout")
	public void logout(){
		
		loggedUser = null;
		
	}

}
