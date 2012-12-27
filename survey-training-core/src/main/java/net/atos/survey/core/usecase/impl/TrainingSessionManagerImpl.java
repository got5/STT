package net.atos.survey.core.usecase.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.RoomDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.OResponse;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;
import net.atos.survey.core.usecase.OResponseManager;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;

@Stateless(name = "net.atos.survey.core.usecase.TrainingSessionManager")
public class TrainingSessionManagerImpl implements TrainingSessionManager {

	@Inject
	TrainingSessionDao trainingSessionDao;
	@Inject
	TrainingDao trainingDao;
	@Inject
	RoomDao roomDao;
	@Inject
	UserDao userDao;
	@Inject
	SurveyManager surveyManager;
	@Inject
	SimpleMCQResponseManager simpleMCQResponseManager;
	@Inject
	OResponseManager oResponseManager;
	@Inject
	ResponseSurveyManager responseSurveyManager;

	public TrainingSession createTrainingSession(Calendar dateS,
			Calendar dateE, Long trainingId, Long roomId)
			throws TrainingNotExistException, RoomNotExistException {
		System.out.println("hello");

		Training training = trainingDao.findById(trainingId);
		Room room = roomDao.findById(roomId);

		if (training == null)
			throw new TrainingNotExistException();
		if (room == null)
			throw new RoomNotExistException();

		TrainingSession trainingSession = new TrainingSession(dateS, dateE,
				training, room);

		return trainingSessionDao.save(trainingSession);

	}

	public TrainingSession updateTrainingSession(TrainingSession trainingSession) {
		return trainingSessionDao.update(trainingSession);
	}

	@Override
	public TrainingSession findById(Long trainingSessionId) {
		return trainingSessionDao.findById(trainingSessionId);
	}

	@Override
	public ResponseSurvey createResponseSurveyWithoutPersist(
			TrainingSession trainingSession) {
		trainingSession = findById(trainingSession.getId());
		Survey survey = trainingSession.getSurvey();

		List<Question> questions = surveyManager.getAllQuestion(survey);
		ResponseSurvey rs = new ResponseSurvey();
		Response r;
		for (Question q : questions) {
			if (q instanceof OQuestion) {
				r = new OResponse(q);
				rs.addResponse(r);
			} else {
				r = new SimpleMCQResponse(q);
				rs.addResponse(r);
			}
		}
		return rs;
	}

	@Override
	public Boolean alreadyAnsweredToSurvey(Long trainingSessionId, Long userId)
			throws UserNotInTrainingSessionException {
		TrainingSession trainingSession = findById(trainingSessionId);
		User user = userDao.findById(userId);

		trainingSession.loadResponses();
		trainingSession.loadUsers();

		if (!trainingSession.getTrainees().contains(user)) {
			throw new UserNotInTrainingSessionException();
		}

		if (trainingSession.getResponseSurvey(user) == null)
			return false;
		else
			return true;

	}

	@Override
	public TrainingSession saveResultForTrainee(Long trainingSessionId,
			Long userId, ResponseSurvey responseSurvey) {

		TrainingSession trainingSession = findById(trainingSessionId);
		User user = userDao.findById(userId);
		List<Response> responses = new ArrayList<Response>();

		for (Response r : responseSurvey.getResponses().values()) {
			responses.add(saveResponse(r));
		}
		responseSurvey.setResponse(responses);
		responseSurvey = responseSurveyManager.save(responseSurvey);

		trainingSession.addResponseSurvey(user, responseSurvey);

		return trainingSessionDao.update(trainingSession);
	}

	private Response saveResponse(Response r) {
		if (r instanceof SimpleMCQResponse)
			return simpleMCQResponseManager.save((SimpleMCQResponse) r);
		else
			return oResponseManager.save((OResponse) r);
	}

	@Override
	public List<TrainingSession> findByTrainee(long userId) throws Exception {
		
	    User loggedUser = userDao.findById(userId);
	    if(loggedUser==null)
	    	throw new UserNotExistException();
	    //get only the elements from the last 6 months
	    return trainingSessionDao.findIncompleteByUser(loggedUser,getDateXMonthBefore(6));
	    
	    
	}
	
	private Calendar getDateXMonthBefore(int month){
		Calendar ret = new GregorianCalendar();
		ret.add(Calendar.MONTH, -month);
		return ret;
	}

}
