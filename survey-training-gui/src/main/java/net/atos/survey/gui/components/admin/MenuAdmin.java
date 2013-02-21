package net.atos.survey.gui.components.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.atos.survey.core.annotation.User1;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import javax.inject.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "context:static/js/accordion.js" }, stylesheet = "context:static/css/menuAdmin.css")
public class MenuAdmin {

	@SessionState
	@Property
	private User loggedUser;

	@Inject
	@User1
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

	@Persist
	private Long trainingSessionId;

	@Property
	@Parameter
	private Calendar from;

	@Property
	@Parameter
	private Calendar to;

	@Property
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
	private AjaxResponseRenderer ajaxRR;

	@Inject
	private Request request;

	@Inject
	private Messages messages;

	@Property
	private SimpleDateFormat dateFormat;

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Property
	private int year;

	@Inject
	private ComponentResources cs;

	private Object value;

	private boolean traineesLoaded = false;

	@SetupRender
	public void applyForActivate() {

		trainees = new ArrayList<User>();

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

	@AfterRender
	public void launchJs() {
		js.addScript(InitializationPriority.LATE, "improveaccordion('%s');",
				"menuTraining" + trainingId);
	}

	public String getClientClassTraineeDone() {
		String ret = "";
		try {
			// ON a plus accès à trainingSession car la boucle sur les trainee
			// est dans une zone
			// Du coup on utiliser trainingSessionId
			if (trainingSessionManager.alreadyAnsweredToSurvey(
					trainingSessionId, trainee.getId())) {
				ret = "trainee-done";
			}
		} catch (UserNotInTrainingSessionException e) {

		}

		return ret;
	}

	public List<Integer> getYears() {

		// on assume que les trainingSession sont ordonné par date
		List<Integer> years = new ArrayList<Integer>();

		trainingSessions = trainingSessionManager.listByCriteria(trainingId,
				instructorId, from, to);

		if (trainingSessions.size() == 0)
			return years;

		TrainingSession cmp = trainingSessions.get(0);
		years.add(cmp.getDateS().get(Calendar.YEAR));

		for (TrainingSession ts : trainingSessions) {

			if (ts.getDateS().get(Calendar.YEAR) != cmp.getDateS().get(
					Calendar.YEAR)) {

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

	@OnEvent(component = "sessionzone")
	public void updateSessionZone(final Long trainingSessionId) {
		// A ce moment ci, on ne connait plus la trainingSession, c'est pourquoi
		// on en garde une référence maintenant
		this.trainingSessionId = trainingSessionId;

		if (!traineesLoaded) {
			trainees = userManager.listTrainees(trainingSessionId);
			traineesLoaded = true;
		}

		cs.triggerEvent("trainingSession", new Long[] { trainingSessionId },
				null);

		arr.addRender("traineesGroup" + trainingSessionId,
				traineeZone.getBody());
		arr.addCallback(new JavaScriptCallback() {

			@Override
			public void run(JavaScriptSupport javascriptSupport) {
				javascriptSupport.addScript("handlerontrainee('%s')",
						"menutrainingsession" + trainingSessionId);

			}
		});

	}

	@Inject
	private AjaxResponseRenderer arr;

	@InjectComponent
	private Zone traineeZone;

	@OnEvent(component = "studentzone")
	public void updateTraineeZone(Long id) {
		cs.triggerEvent("trainee", new Long[] { id }, null);

	}

}
