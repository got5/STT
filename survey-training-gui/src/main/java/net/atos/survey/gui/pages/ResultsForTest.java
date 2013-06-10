package net.atos.survey.gui.pages;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.List;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import org.apache.tapestry5.EventConstants;

import org.apache.tapestry5.annotations.Import;

import org.apache.tapestry5.annotations.OnEvent;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import org.apache.tapestry5.ioc.annotations.Inject;


@Import(library = { "context:static/js/dateFromTo.js",
		"context:static/js/accordion.js", "context:static/js/autocomplete.js" }, stylesheet = "context:static/css/results.css")
public class ResultsForTest {

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


	/* result */

	@Property
	private List<Training> trainings;

	@Property
	private Training training;

    @Property
    private Long trainingSessionId;

    @Property
    private Long traineeId;





	@Property
	private SimpleDateFormat dateFormat;

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";



	@OnEvent(EventConstants.ACTIVATE)
	public Object applyForActivate() {
        String tapestry =   "Tapestry Basic";

		if (!loggedUserExists) {
			return Index.class;
		}

		List<Training> licot = userManager.loadInChargeOf(loggedUser.getId());

		if (licot.size() == 0) {
          return Index.class;
		}
        

        Training training = trainingManager.listTrainingName(tapestry).get(0);

        User instructor = userManager.listInstructor("laurent").get(0);
        
        Calendar d0 = new GregorianCalendar();
        d0.add(Calendar.MONTH,-2);
        Calendar d1 = new GregorianCalendar();
        
       
        TrainingSession trainingSession = trainingSessionManager.listByCriteria(training.getId(),instructor.getId(),d0,d1).get(0);
        trainingSessionId = trainingSession.getId();

        List<User> trainees = userManager.listTrainees(trainingSessionId);

        for (User u:trainees){
            try {
                if(trainingSessionManager.alreadyAnsweredToSurvey(trainingSessionId, u.getId())){
                    traineeId = u.getId();
                    break;
                }
            } catch (UserNotInTrainingSessionException e) {

            }
        }


        return null;

	}








}
