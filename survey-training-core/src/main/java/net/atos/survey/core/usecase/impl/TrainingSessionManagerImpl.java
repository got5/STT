package net.atos.survey.core.usecase.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;

@Stateless(name = "net.atos.survey.core.usecase.TrainingSessionManager")
public class TrainingSessionManagerImpl implements TrainingSessionManager {

	@EJB
	TrainingSessionDao trainingSessionDao;
	@EJB
	TrainingDao trainingDao;
	@EJB
	RoomDao roomDao;
	@EJB
	UserDao userDao;
	@EJB
	SurveyManager surveyManager;

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

}
