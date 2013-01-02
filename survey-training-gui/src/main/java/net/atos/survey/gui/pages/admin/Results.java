package net.atos.survey.gui.pages.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;

@Import(library = { "context:static/js/dateFromTo.js" }, stylesheet = "context:static/css/results.css")
public class Results {

	@SessionState
	@Property
	private User loggedUser;

	@Property
	private boolean loggedUserExists;

	@Component(id = "searchForm")
	private Form searchForm;

	@Inject
	private Messages messages;

	@Inject
	private UserManager userManager;

	@Inject
	private TrainingManager trainingManager;

	@Property
	private String trainingName;

	@Property
	private String instructorName;

	
	@Property
	private Date fromD;

	
	@Property
	private Date toD;

	@Inject
	private AssetSource as;

	public JSONObject getOptions() {

		return new JSONObject().put("dateFormat", messages.get("datePatternJquery"))
				.put("showOn", "focus")
				.put("onSelect", new JSONLiteral("onSelectClosure()"));

	}

	@OnEvent(value = "provideCompletions", component = "training")
	public List<String> provideTrainingCompletion(String training) {
		return trainingManager.listTrainingName(trainingName);

	}

	@OnEvent(value = "provideCompletions", component = "instructor")
	public List<String> provideInstructorCompletion(String mot) {
		return userManager.listInstructor(mot);
	}

	public void setFromD(String date) {
		
	}

	public String getFromD() {
		return fromD.toString();
	}

	public void setToD(String date) {

	}

	public String getToD() {
		return toD.toString();
	}

}
