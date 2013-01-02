package net.atos.survey.core.usecase;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TypeTraining;
import net.atos.survey.core.exception.UserNotExistException;

@Local
public interface TrainingManager {

	Training createTraining(String name,TypeTraining typeTraining,long managerId)throws UserNotExistException;
	
	List<String>listTrainingName(String trainingName);
	
	
}
