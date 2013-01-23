package net.atos.survey.gui.components.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
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

	@Property
	private List<User> trainees=new ArrayList<User>();

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
	ComponentResources cs;
	
	private boolean traineesLoaded=false;

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

	
	
	@AfterRender
	public void launchJs() {
		js.addScript(InitializationPriority.LATE, "improveaccordion('%s');","menuTraining"+trainingId);
	}
	
	public List<Integer> getYears() {
		
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
	
	
	private Object value;
	
	@OnEvent(component = "sessionzone")
	public void updateSessionZone(final Long id) {
		 
		if(!traineesLoaded){
			 trainees = userManager.listTrainees(id);
			traineesLoaded=true;
		}
		
		cs.triggerEvent("trainingSession",new Long[]{id},null);
		
		arr.addRender("traineesGroup"+id, traineeZone.getBody());
		arr.addCallback(new JavaScriptCallback() {
			
			@Override
			public void run(JavaScriptSupport javascriptSupport) {
				javascriptSupport.addScript("handlerontrainee('%s')","menutrainingsession"+id );
				
			}
		});
		
	}
	
	@Inject
	private AjaxResponseRenderer arr;
	
	@InjectComponent private Zone traineeZone;
	
	@OnEvent(component = "studentzone")
	public void updateTraineeZone(Long id) {
		cs.triggerEvent("trainee",new Long[]{id},null);
			
	}
}
