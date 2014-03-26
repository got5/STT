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

	TrainingSession createTrainingSession(Calendar dateS,Calendar dateE, Long trainingId,Long instructorId,Long roomId) throws TrainingNotExistException,RoomNotExistException;
	
	TrainingSession updateTrainingSession(TrainingSession trainingSession);
	
	TrainingSession findById(Long trainingSessionId,Long userId);
	
	TrainingSession findById(Long trainingSessionId);

    TrainingSession findByIdWithTrainees(Long trainingSessionId);
	
	ResponseSurvey createResponseSurveyWithoutPersist(TrainingSession trainingSession);
	
	Boolean alreadyAnsweredToSurvey(Long trainingSessionId,Long userId) throws UserNotInTrainingSessionException;
	
	TrainingSession saveResultForTrainee(Long trainingSessionId,Long userId,ResponseSurvey responseSurvey);

	List<TrainingSession> findByTrainee(long id) throws Exception;

	List<TrainingSession> listByCriteria(Long trainingId, Long instructorId,
			Calendar from, Calendar to);
	
	TrainingSession loadTrainees(Long trainingSessionId);

	TrainingSession loadAll(Long id);
	
	public TrainingSession loadResponses(Long id);

	Long applyForTodayTapestrySession(User newUser);

	TrainingSession removeTrainee(Long trainingSessionId, Long traineeId);
	
	void delete(Long trainingSessionId);

    public TrainingSession applyForSession(TrainingSession trainingSession, User newUser);
}
