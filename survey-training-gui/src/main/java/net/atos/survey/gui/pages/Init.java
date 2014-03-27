package net.atos.survey.gui.pages;

import net.atos.survey.core.entity.*;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.usecase.InitManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;

import net.atos.survey.core.usecase.UserManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import javax.inject.Inject;
import java.util.List;


public class Init {


    @Inject
    InitManager initManager;

    @Inject
    UserManager userManager;


    @OnEvent(value = EventConstants.ACTIVATE)
    public Object active() {

        if (userManager.countUser() != 0) {
            return Index.class;
        }
        return null;
    }

    /*@OnEvent(value = EventConstants.ACTION, component = "initdb")
    public void clickOnForward() {

        initManager.initDB();
        try {
            initManager.testOneSurvey();
        } catch (UserNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TrainingNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotInitaliazedSurveyDataBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RoomNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/


}
