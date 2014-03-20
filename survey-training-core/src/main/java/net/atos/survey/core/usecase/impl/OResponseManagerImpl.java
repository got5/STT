package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.OResponseDao;
import net.atos.survey.core.entity.OResponse;
import net.atos.survey.core.usecase.OResponseManager;

@Stateless
public class OResponseManagerImpl implements OResponseManager {

	@Inject
	OResponseDao oResponseDao;

	@Override
	public OResponse save(OResponse oResponse) {
		return oResponseDao.save(oResponse);
	}
	
	
	

	
}
