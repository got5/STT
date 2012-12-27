package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.ChoiceDao;
import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.usecase.ChoiceManager;

@Stateless(name="net.atos.survey.core.usecase.ChoiceManager")
public class ChoiceManagerImpl implements ChoiceManager {
	
	@Inject 
	ChoiceDao choiceDao;

	@Override
	public Choice findById(Long id) {
		return choiceDao.findById(id);
	}

	

}
