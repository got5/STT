package net.atos.survey.gui.components;

import java.util.List;

import javax.inject.Inject;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;
import net.atos.survey.gui.pages.Index;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;


@Import(stylesheet = "context:static/css/layout.css")
public class Layout {

	@Inject
	private UserManager userManager;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	@SessionState
	private User loggedUser;

	@Property
	private boolean loggedUserExists;

	public String getTitle() {
		return title;
	}

	@OnEvent(value = EventConstants.ACTION, component = "logout")
	public Object logout() {

		loggedUser = null;
		return Index.class;

	}

	public boolean isAdmin() {
		boolean ret = false;

		if (loggedUserExists) {
			List<Training> licot = userManager.loadInChargeOf(loggedUser
					.getId());
			if (licot.size() != 0) {

				ret = true;
			}
		}

		return ret;
	}

}
