package net.atos.survey.gui.pages.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;
import net.atos.survey.gui.components.admin.MenuAdmin;
import net.atos.survey.gui.pages.Index;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import javax.inject.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "context:static/js/dateFromTo.js",
		"context:static/js/accordion.js", "context:static/js/autocomplete.js" }, stylesheet = "context:static/css/results.css")
public class Results {

	@SessionState
	@Property
	private User loggedUser;

	@Property
	private boolean loggedUserExists;

	/* EJB */
	@Inject
	private UserManager userManager;

	@Inject
	private TrainingManager trainingManager;

	@Inject
	private TrainingSessionManager trainingSessionManager;

	@Property
	private TrainingSession sessionForBlock;

	@Property
	private User traineeForBlock;

	@Inject
	private SimpleMCQResponseManager simpleMCQResponseManager;

	/* filled by the form */

	@Property
	private String trainingName;

	@Property
	private Long trainingId;

	@Property
	private Long instructorId;

	@Property
	private String instructorName;

	/* result */

	@Property
	private List<Training> trainings;

	@Property
	private Training training;

	@Property
	private Date fromD;

	@Property
	private Calendar from;

	@Property
	private Date toD;

	@Property
	private Calendar to;

	@Component(id = "searchForm")
	private Form searchForm;

	@Inject
	private AssetSource as;

	@Inject
	private JavaScriptSupport js;

	@Inject
	private Messages messages;

	@Property
	private SimpleDateFormat dateFormat;

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Component
	private Zone zone1;

	@Component
	private Zone zone3;
	
	@Component
	private Zone zone4;

	@Inject
	private Block sessionBlock;

	@Inject
	private Block traineeBlock;

	@Persist
	@Property
	private Long trainingSessionIdClicked;

	@Persist
	@Property
	private Long traineeIdClicked;

	@Inject
	private Request request;

	@Property
	private int year;

	@InjectComponent
	private MenuAdmin menuAdmin;

	@Inject
	private AlertManager manager;

	@Component
	private Submit addSubmit;

	private boolean add = false;

	@OnEvent(EventConstants.ACTIVATE)
	public Object applyForActivate() {

		if (!loggedUserExists) {
			return Index.class;
		}

		List<Training> licot = userManager.loadInChargeOf(loggedUser.getId());
		if (licot.size() == 0) {
			return Index.class;
		}

		if (messages.contains("datePattern")) {
			try {
				dateFormat = new SimpleDateFormat(
						messages.get("datePatternJava"));
			} catch (Exception ex) {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}

		if (!request.isXHR())
			applyForSuccess();

		return null;
	}

	@OnEvent(value = "selected", component = "addSubmit")
	public void submitFromAdd() {
		this.add = true;

	}

	@OnEvent(value = EventConstants.SUCCESS)
	public Block applyForSuccess() {

		reset();

		if (fromD != null) {
			from = new GregorianCalendar();
			from.setTime(fromD);
			System.out.println("from " + " day "
					+ from.get(Calendar.DAY_OF_MONTH) + " month "
					+ from.get(Calendar.MONTH) + " year "
					+ from.get(Calendar.YEAR));
		}

		if (toD != null) {
			to = new GregorianCalendar();
			to.setTime(toD);
			System.out.println("to " + " day " + to.get(Calendar.DAY_OF_MONTH)
					+ " month " + to.get(Calendar.MONTH) + " year "
					+ to.get(Calendar.YEAR));
		}

		if (add) {
			if (isAllSet()) {
				addSession();
				return zone3.getBody();
			}
			else{
				return zone4.getBody();
			}
		} else {
			trainings = trainingManager.listTrainingName(trainingName);
			return zone1.getBody();
		}

	}

	private void reset() {
		training = null;

		if (instructorName == null)
			instructorId = null;
		if (trainingName == null)
			trainingId = null;

		from = null;
		to = null;
	}

	@OnEvent(value = "provideCompletions", component = "training")
	public List<JSONObject> provideTrainingCompletion(String mot) {

		List<Training> trainings = trainingManager.listTrainingName(mot);
		List<JSONObject> strings = new ArrayList<JSONObject>();

		for (Training t : trainings)
			strings.add(new JSONObject("id", t.getId() + "", "value", t
					.getName()));

		return strings;
	}

	@OnEvent(value = "provideCompletions", component = "instructor")
	public List<JSONObject> provideInstructorCompletion(String mot) {

		List<User> instructors = userManager.listInstructor(mot);
		List<JSONObject> strings = new ArrayList<JSONObject>();

		for (User i : instructors)
			strings.add(new JSONObject("id", i.getId() + "", "value", i
					.getFirstName() + " " + i.getName()));
		return strings;
	}

	public JSONObject getOptions() {

		return new JSONObject().put("dateFormat",
				messages.get("datePatternJquery")).put("showOn", "focus");
		// .put("onSelect", new JSONLiteral("onSelectClosure()"));

	}

	@AfterRender
	public void launchJs() {

		js.addScript(InitializationPriority.LATE, "customAutocomplete(%s);",
				new JSONArray());

	}

	@Inject
	private AjaxResponseRenderer arr;

	@OnEvent(component = "menuAdmin", value = "trainingSession")
	public void updateSessionZone(Long id) {
		trainingSessionIdClicked = id;
		// return sessionBlock;
		arr.addRender("zone2", sessionBlock);
	}

	@OnEvent(component = "menuAdmin", value = "trainee")
	public void updateTraineeZone(Long id) {
		traineeIdClicked = id;
		// return traineeBlock;
		arr.addRender("zone2", traineeBlock);
	}

	public void addSession() {
		Training training = trainingManager.findById(trainingId);

		try {
			trainingSessionManager.createTrainingSession(from, to, trainingId,
					instructorId, training.getDefaultRoom().getId());
			manager.alert(Duration.SINGLE, Severity.INFO, "New Session created");
		} catch (Exception e) {
			manager.alert(Duration.SINGLE, Severity.INFO,
					"Error occured while creating a new session");
			e.printStackTrace();
		}

	}

	public boolean isAllSet() {
		boolean ret = false;
		if (trainingId != null && instructorId != null && from != null
				&& to != null) {
			ret = true;
		}
		return ret;
	}

}
