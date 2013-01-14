package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Stateless(name="net.atos.survey.core.dao.ResponseSurveyDao")
public class ResponseSurveyDaoImpl extends DaoImpl<Long, ResponseSurvey> implements ResponseSurveyDao {

	@Override
	public ResponseSurvey findByTrainingSessionAndByTrainee(TrainingSession ts,
			User trainee) {
	    String queryString = "select rs from TrainingSession ts join ts.responses rss join ResponseSurvey rs "
	    		+" where ts = ?1"
	    		+" and KEY(rss) = ?2"
	    		+" and VALUE(rss) = rs "
	    		;
	    		
	    		return find(null, null, queryString, ts,trainee);
	}

	
	
	
}
