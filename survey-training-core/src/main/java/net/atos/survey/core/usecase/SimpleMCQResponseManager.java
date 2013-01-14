package net.atos.survey.core.usecase;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.SimpleMCQResponse;


@Local
public interface SimpleMCQResponseManager {
	
	SimpleMCQResponse save (SimpleMCQResponse simpleMCQResponse);
	
	List<SimpleMCQResponse> listResponseForStatistics(Long trainingId,Long instructorId,Calendar from,Calendar to);
	
	
	

}
