package net.atos.survey.core.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;





@Local
public interface SimpleMCQResponseDao extends Dao<Long, SimpleMCQResponse> {

	List<SimpleMCQResponse> listForStatistics(Training training,
			User instructor, Calendar from, Calendar to);


	
}
