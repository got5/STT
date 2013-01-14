package net.atos.survey.core.usecase.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.SimpleMCQResponseDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;

@Stateless(name = "net.atos.survey.core.usecase.SimpleMCQResponseManager")
public class SimpleMCQResponseManagerImpl implements SimpleMCQResponseManager {

	@Inject
	SimpleMCQResponseDao simpleMCQResponseDao;
	@Inject
	TrainingDao trainingDao;
	@Inject
	UserDao userDao;
	
	
	@Override
	public SimpleMCQResponse save(SimpleMCQResponse simpleMCQResponse) {
		
		return simpleMCQResponseDao.save(simpleMCQResponse);
	}

	@Override
	public List<SimpleMCQResponse> listResponseForStatistics(Long trainingId,
			Long instructorId, Calendar from, Calendar to) {
				
		Training training = trainingDao.findById(trainingId);
		User instructor = userDao.findById(instructorId);
		
		return simpleMCQResponseDao.listForStatistics(training,instructor,from,to);
		
	}
	


	

}
