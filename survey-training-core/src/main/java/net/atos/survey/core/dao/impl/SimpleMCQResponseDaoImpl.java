package net.atos.survey.core.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.SimpleMCQResponseDao;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;

@Stateless
public class SimpleMCQResponseDaoImpl extends DaoImpl<Long, SimpleMCQResponse> implements SimpleMCQResponseDao {

	@Override
	public List<SimpleMCQResponse> listForStatistics(Training training,
			User instructor, Calendar from, Calendar to) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
