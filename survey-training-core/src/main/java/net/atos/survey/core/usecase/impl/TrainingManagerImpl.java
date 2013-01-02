package net.atos.survey.core.usecase.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TypeTraining;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.usecase.TrainingManager;

@Stateless(name="net.atos.survey.core.usecase.TrainingManager")
public class TrainingManagerImpl implements TrainingManager{

	@Inject TrainingDao trainingDao;
	@Inject UserDao userDao;
	
	@Override
	public Training createTraining(String name, TypeTraining typeTraining,
			long managerId) throws UserNotExistException {
		
		User owner = userDao.findById(managerId);
		
		if(owner==null)
			throw new UserNotExistException();
		
		Training training = new Training(name, typeTraining);
		training.addUserInCharge(owner);
		return trainingDao.save(training);
	}

	@Override
	public List<String> listTrainingName(String trainingName) {
		
		List<Training> temp=  trainingDao.listNameByName(trainingName);
		List<String> ret = new ArrayList<String>();
		for(Training t:temp){
			ret.add(t.getName());
		}
		return ret;
			
	}

}
