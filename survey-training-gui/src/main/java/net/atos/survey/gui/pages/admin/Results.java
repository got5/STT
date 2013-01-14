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

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
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

	@Property(read = false)
	private List<User> trainees;

	@Property
	private User trainee;

	@Property
	private List<TrainingSession> trainingSessions;

	@Property
	private TrainingSession trainingSession;

	@Property
	private Date fromD;

	private Calendar from;

	@Property
	private Date toD;

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

	@Inject
	private Block sessionBlock;

	@Inject
	private Block traineeBlock;

	@Property
	private TrainingSession sessionForBlock;

	@Inject
	private AjaxResponseRenderer ajaxRR;

	@Inject
	private Request request;

	@Property
	private User traineeForBlock;

	@Property
	private int year;

	@OnEvent(EventConstants.ACTIVATE)
	public void applyForActivate() {

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

		trainings = trainingManager.listTrainingName(trainingName);

		if (request.isXHR()) {
			ajaxRR.addCallback(new JavaScriptCallback() {

				public void run(JavaScriptSupport javascriptSupport) {
					javascriptSupport.addScript("improveaccordion();");

				}
			});
		}

		return zone1.getBody();

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

	public List<User> getTrainees() {

		return userManager.listTrainees(trainingSession.getId());
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
		
		// improve autocomplete
		js.addScript(InitializationPriority.LATE, "customAutocomplete(%s);",
				new JSONArray());
		// js.addInitializerCall(InitializationPriority.LATE,
		// "customautocomplete", new JSONObject());

		js.addScript(InitializationPriority.LATE, "improveaccordion();");
	}

	@OnEvent(component = "sessionzone")
	public Block updateSessionZone(Long id) {
		sessionForBlock = trainingSessionManager.loadAll(id);
		return sessionBlock;
	}

	@OnEvent(component = "studentzone")
	public Block updateTraineeZone(Long id) {
		traineeForBlock = userManager.findById(id);
		return traineeBlock;
	}

	
	public List<Integer> getYears() {
		System.out.println("coucou");
		// on assume que les trainingSession sont ordonné par date
		List<Integer> years = new ArrayList<Integer>();

		trainingSessions = trainingSessionManager.listByCriteria(
				training.getId(), instructorId, from, to);

		if (trainingSessions.size() == 0)
			return years;

		TrainingSession cmp = trainingSessions.get(0);
		years.add(cmp.getDateS().get(Calendar.YEAR));

		for (TrainingSession ts : trainingSessions) {
			
			if (ts.getDateS().get(Calendar.YEAR) != cmp.getDateS().get(
					Calendar.YEAR)) {
				System.out.println("ts " + ts.getDateS().get(Calendar.YEAR));
				System.out.println("cmp " + cmp.getDateS().get(Calendar.YEAR));

				years.add(ts.getDateS().get(Calendar.YEAR));
				cmp = ts;
			}
			
		}
		return years;

	}

	public List<TrainingSession> getTrainingSessionsByYear() {
		List<TrainingSession> tss = new ArrayList<TrainingSession>();
		for (TrainingSession ts : trainingSessions) {
			if (ts.getDateS().get(Calendar.YEAR) == year)
				tss.add(ts);
		}
		return tss;
	}

}
