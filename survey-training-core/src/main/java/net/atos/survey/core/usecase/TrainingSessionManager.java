package net.atos.survey.core.usecase;

import java.util.Calendar;

import javax.ejb.Local;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;


@Local
public interface TrainingSessionManager{

	TrainingSession createTrainingSession(Calendar dateS,Calendar dateE, Long trainingId,Long roomId) throws TrainingNotExistException,RoomNotExistException;
	
	TrainingSession updateTrainingSession(TrainingSession trainingSession);
	
	TrainingSession findById(Long trainingSessionId);
	
	ResponseSurvey createResponseSurveyWithoutPersist(TrainingSession trainingSession);
}
