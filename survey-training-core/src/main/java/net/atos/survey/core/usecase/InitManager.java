package net.atos.survey.core.usecase;

import javax.ejb.Local;

@Local
public interface InitManager {

	void initDB();
	
	void testOneSurvey() throws Exception;
	
	
	
}
