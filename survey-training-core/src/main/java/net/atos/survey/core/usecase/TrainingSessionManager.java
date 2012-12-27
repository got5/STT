package net.atos.survey.core.usecase;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;


@Local
public interface TrainingSessionManager{

	TrainingSession createTrainingSession(Calendar dateS,Calendar dateE, Long trainingId,Long roomId) throws TrainingNotExistException,RoomNotExistException;
	
	TrainingSession updateTrainingSession(TrainingSession trainingSession);
	
	TrainingSession findById(Long trainingSessionId);
	
	ResponseSurvey createResponseSurveyWithoutPersist(TrainingSession trainingSession);
	
	Boolean alreadyAnsweredToSurvey(Long trainingSessionId,Long userId) throws UserNotInTrainingSessionException;
	
	TrainingSession saveResultForTrainee(Long trainingSessionId,Long userId,ResponseSurvey responseSurvey);

	List<TrainingSession> findByTrainee(long id) throws Exception;

}
