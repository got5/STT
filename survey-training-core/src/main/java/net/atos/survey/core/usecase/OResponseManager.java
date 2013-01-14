package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.OResponse;


@Local
public interface OResponseManager {
	
	OResponse save(OResponse oResponse);
	
	
	

}
