package net.atos.survey.core.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;





@Local
public interface TrainingSessionDao extends Dao<Long, TrainingSession> {

	List<TrainingSession> findIncompleteByUser(User loggedUser, Calendar dateXMonthBefore);

	List<TrainingSession> listByCriteria(Training training, User instructor,
			Calendar from, Calendar to);


	
}
