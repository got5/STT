package net.atos.survey.gui.pages.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.*;
import net.atos.survey.gui.pages.Index;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"context:static/js/dateFromTo.js",
        "context:static/js/accordion.js", "context:static/js/autocomplete.js"}, stylesheet = "context:static/css/results.css")
public class CreateSession {

    @SessionState
    @Property
    private User loggedUser;

    @Property
    private boolean loggedUserExists;

    @Property
    private String errorTitle;

    @Property
    private String errorMessage;

    /* EJB */
    @Inject
    private UserManager userManager;

    @Inject
    private TrainingManager trainingManager;

    @Inject
    private TrainingSessionManager trainingSessionManager;

    @Inject
    private SimpleMCQResponseManager simpleMCQResponseManager;


    @Inject
    private RoomManager roomManager;

	/* filled by the form */

    private List<Training> inchargeOfTrainings;

    @Property
    private String roomName;

    @Property
    private String trainingName;

    @Property
    private String getRoomName;

    @Property
    private Long trainingId;

    @Property
    private Long instructorId;

    @Property
    private Long roomId;

    @Property
    private String instructorName;

	/* result */

    @Property
    private Room room;

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

    @Property
    private List<TrainingSession> trainingSessions;

    @Property
    private TrainingSession trainingSession;

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    @Component
    private Zone zone2;

    @Component
    private Zone zone3;

    @Component
    private Zone zone4;


    @Inject
    private Request request;

    @Property
    private int year;

    @Inject
    private AlertManager alertManager;

    @Component
    private Submit addSubmit;

    private boolean add = false;

    @Inject
    private AjaxResponseRenderer arr;


    @OnEvent(EventConstants.ACTIVATE)
    public Object applyForActivate() {

        if (!loggedUserExists) {
            return Index.class;
        }

        inchargeOfTrainings = userManager.loadInChargeOf(loggedUser.getId());
        if (inchargeOfTrainings.size() == 0) {
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
        }

        if (toD != null) {
            to = new GregorianCalendar();
            to.setTime(toD);
        }

        if (add) {
            if (isAllSet()) {
                Training training = trainingManager.findById(trainingId);
                if (isAllowed(training)) {
                    if(addSession()) {
                        //Alright zone refreshed soon
                    } else {
                        return errorOccurred("Internal Error", "An error occurred while adding a session.");
                    }
                } else {
                    return notAllowed();
                }
            } else {
                return notSet();
            }
        }
        if (from == null) {
            from = new GregorianCalendar();
        }
        trainingSessions = trainingSessionManager.listByCriteria(trainingId, instructorId, from, to);
        return zone2.getBody();


    }

    private void reset() {
        training = null;

        if (instructorName == null)
            instructorId = null;
        if (trainingName == null)
            trainingId = null;
        if (roomName == null)
            roomId = null;

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

    @OnEvent(value = "provideCompletions", component = "room")
    public List<JSONObject> provideRoomCompletion(String mot) {

        List<Room> rooms = roomManager.listRoomName(mot);
        List<JSONObject> strings = new ArrayList<JSONObject>();

        for (Room t : rooms)
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
                messages.get("datePatternJquery")).put("showOn", "focus")
                .put("onSelect", new JSONLiteral("onSelectClosure()"))
                .put("minDate", -3);
    }

    @AfterRender
    public void launchJs() {

        js.addScript(InitializationPriority.LATE, "customAutocomplete(%s);",
                new JSONArray());

    }


    public boolean addSession() {
        try {

            trainingSessionManager.createTrainingSession(from, to, trainingId,
                    instructorId, roomId);
            roomName = null;
            trainingName = null;
            instructorName = null;
            reset();

            return true;
        } catch (Exception e) {
            alertManager.alert(Duration.SINGLE, Severity.SUCCESS,
                    "Error occured while creating a new session");
            e.printStackTrace();
            return false;
        }
    }

    private boolean isAllowed(Training training) {
        return inchargeOfTrainings.contains(training);
    }

    public boolean isAllSet() {
        boolean ret = false;
        if (trainingId != null && instructorId != null && from != null
                && to != null) {
            if (roomId != null) {
                ret = true;
            } else if (roomName != null && !roomName.equals("")) {
                /** Create the room if doens't exist*/
                Room r = roomManager.createRoom(roomName);
                if (r != null) {
                    roomId = r.getId();
                    ret = true;
                }
            }
        }
        return ret;
    }

    public boolean isNoSession() {
        return trainingSessions.size() == 0;

    }

    public Object onActionFromDeleteSession(long sessionId) {
        Training training = trainingSessionManager.findById(sessionId).getTraining();
        if (isAllowed(training)) {
            trainingSessionManager.delete(sessionId);
            trainingSessions = trainingSessionManager.listByCriteria(null, null, new GregorianCalendar(), null);
            return zone2.getBody();
        }
        return notAllowed();
    }

    private Block notAllowed() {
       return errorOccurred("Sorry", "You don't have permission to manage this training");
    }

    private Block notSet() {
       return errorOccurred("Form error", "All field are mandatory"); 
    }

    private Block errorOccurred(String title, String message){
        errorMessage = message;
        errorTitle = title;
        return zone4.getBody();
    }

}
