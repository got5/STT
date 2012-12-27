package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.SimpleMCQResponseDao;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;

@Stateless(name = "net.atos.survey.core.usecase.SimpleMCQResponseManager")
public class SimpleMCQResponseManagerImpl implements SimpleMCQResponseManager {

	@Inject
	SimpleMCQResponseDao simpleMCQResponseDao;

	@Override
	public SimpleMCQResponse save(SimpleMCQResponse simpleMCQResponse) {
		
		return simpleMCQResponseDao.save(simpleMCQResponse);
	}
	


	

}
