package net.atos.survey.core.usecase.impl;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.RoomDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.exception.RoomNotExistException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.usecase.TrainingSessionManager;


@Stateless(name="net.atos.survey.core.usecase.TrainingSessionManager")
public class TrainingSessionManagerImpl implements TrainingSessionManager{
	
	@EJB TrainingSessionDao trainingSessionDao;
	@EJB TrainingDao trainingDao;
	@EJB RoomDao roomDao;

	public TrainingSession createTrainingSession(Calendar dateS,Calendar dateE, Long trainingId,Long roomId) throws TrainingNotExistException,RoomNotExistException{
		System.out.println("hello");
		
		Training training = trainingDao.findById(trainingId);
		Room room = roomDao.findById(roomId);
		
		if(training == null )
			throw new TrainingNotExistException();
		if(room == null)
			throw new RoomNotExistException();
		
		TrainingSession trainingSession = new TrainingSession(dateS, dateE, training,room );
		
		return trainingSessionDao.save(trainingSession);
		
		
	}
	
	public TrainingSession updateTrainingSession(TrainingSession trainingSession){
		return trainingSessionDao.update(trainingSession);
	}
	
	
	
}
