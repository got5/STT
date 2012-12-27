package net.atos.survey.core.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;

import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Stateless(name="net.atos.survey.core.dao.TrainingSessionDao")
public class TrainingSessionDaoImpl extends DaoImpl<Long, TrainingSession> implements TrainingSessionDao {

	@Override
	public List<TrainingSession> findIncompleteByUser(User loggedUser,
			Calendar dateXMonthBefore) {
		
		String orderQuery = " order by t.dateS DESC ";
		String queryString = "select t from TrainingSession t "
				+" where not exists ( select r.id from t.responses r "
				    +" where key(r) = ?1) " 
				+ " and t.dateS > ?2 "
				;
		
		queryString+=orderQuery;
		
		return list(null,null, queryString, loggedUser,dateXMonthBefore,TemporalType.DATE);
	
	}

	
	
	
}
