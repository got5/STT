package net.atos.survey.gui.components.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "context:static/js/accordion.js" }, stylesheet = "context:static/css/menuAdmin.css")
public class MenuAdmin {

	@SessionState
	@Property
	private User loggedUser;

	@Inject
	private UserManager userManager;

	@Inject
	private TrainingManager trainingManager;

	@Inject
	private TrainingSessionManager trainingSessionManager;

	@Property
	@Parameter
	private Long trainingId;

	@Property
	@Parameter
	private Long instructorId;

	@Property
	@Parameter
	private Calendar from;

	@Property
	@Parameter
	private Calendar to;

	@Property(read = false)
	private List<User> trainees;

	@Property
	private User trainee;

	@Property
	private List<TrainingSession> trainingSessions;

	@Property
	private TrainingSession trainingSession;

	@Inject
	private AssetSource as;

	@Inject
	private JavaScriptSupport js;

	@Inject
	private Messages messages;

	@Property
	private SimpleDateFormat dateFormat;

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Property
	private int year;

	@SetupRender
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
	}

	public List<User> getTrainees() {

		return userManager.listTrainees(trainingSession.getId());
	}
	
	@AfterRender
	public void launchJs() {
		js.addScript(InitializationPriority.LATE, "improveaccordion();");
	}
	
	public List<Integer> getYears() {
		System.out.println("coucou");
		// on assume que les trainingSession sont ordonné par date
		List<Integer> years = new ArrayList<Integer>();

		trainingSessions = trainingSessionManager.listByCriteria(
				trainingId, instructorId, from, to);

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
