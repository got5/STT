package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Stateless
public class ResponseSurveyDaoImpl extends DaoImpl<Long, ResponseSurvey> implements ResponseSurveyDao {

	@Override
	public ResponseSurvey findByTrainingSessionAndByTrainee(Long tsId,
			User trainee) {
		
		
		
		
		String q = "select value(rss) from TrainingSession ts join ts.responses rss "
				+" where ts.id = ?1 " 
				+" and key(rss) = ?2 "
				;
	    
		
	    		
	    		return find(q,tsId,trainee);
	}

	
	
	
}
