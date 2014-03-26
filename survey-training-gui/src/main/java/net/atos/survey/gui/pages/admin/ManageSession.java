package net.atos.survey.gui.pages.admin;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;
import net.atos.survey.gui.pages.Index;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

@Import(library = {  "context:static/js/autocompleteWithSelect.js" }, stylesheet = "context:static/css/results.css")
public class ManageSession {

    @SessionState
    @Property
    private User loggedUser;

    @Inject
    private UserManager userManager;

    @Inject
    private TrainingSessionManager trainingSessionManager;

    @Inject
    private ComponentResources componentResources;

    @Inject
    private Messages messages;

    @Property
    private boolean loggedUserExists;

    @Property
    private SimpleDateFormat dateFormat;

    private Long tsId;

    @Property
    private User trainee;

    private List<Training> inChargeOfTrainings;

    @Property
    private TrainingSession trainingSession;

    @Property
    private String newUserName;

    @Property
    private User newUser;

    @Property
    private Long newUserId;

    @Component
    private Zone zone1;


    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    @OnEvent(EventConstants.PASSIVATE)
    public Long passive(){
        return tsId;
    }


    @OnEvent(EventConstants.ACTIVATE)
    public Object applyForActivate(Long trainingSessionId) {
        this.tsId = trainingSessionId;

        trainingSession = trainingSessionManager.findByIdWithTrainees(trainingSessionId);

        if (!loggedUserExists) {
            return Index.class;
        }

        inChargeOfTrainings = userManager.loadInChargeOf(loggedUser.getId());
        if (inChargeOfTrainings.size() == 0) {
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


        return null;
    }

    public boolean isNoTrainees() {
        List<User> t = trainingSession.getTrainees();
        if(t== null)
            return true;

        return t.size() == 0;
    }

    private boolean isAllowed(Training training) {
        return inChargeOfTrainings.contains(training);
    }


    public Object onActionFromDeleteTrainee(Long userId) {
        Training training = trainingSession.getTraining();
        if (isAllowed(training)) {
            trainingSession = trainingSessionManager.removeTrainee(trainingSession.getId(), userId);
            return zone1.getBody();
        }

        return null;
    }

    public JSONObject getOptions() {

        return new JSONObject().put("select",new JSONLiteral("onSelectedAutocompleterClosure()"))
                .put("url", componentResources.createEventLink("addTrainee").toAbsoluteURI());
    }


    @OnEvent(value = "provideCompletions", component = "newUser")
    public List<JSONObject> provideTrainingCompletion(String mot) {

       List<User> users = userManager.listTrainees(mot);
        List<JSONObject> strings = new ArrayList<JSONObject>();

        for (User u : users)
            strings.add(new JSONObject("id", u.getId() + "", "value", u.getDisplayName()));

        return strings;
    }

    @OnEvent(value="addTrainee")
    public Object selectUser(Long userId){
        Training training = trainingSession.getTraining();
        if (isAllowed(training)) {
            User user = userManager.findById(userId);
            trainingSession = trainingSessionManager.applyForSession(trainingSession, user);
            return zone1.getBody();
        }

        return null;
    }



    public Object addTrainee(){
        Training training = trainingSession.getTraining();
        if (isAllowed(training)) {
            trainingSession = trainingSessionManager.applyForSession(trainingSession, newUser);
            return zone1.getBody();
        }
        return null;
    }


}