package net.atos.survey.core.usecase.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;


@Stateless(name="net.atos.survey.core.usecase.UserManager")
public class UserManagerImpl implements UserManager {

	@Inject
	UserDao userDao;
	@Inject
	TrainingSessionDao trainingSessionDao;
	
	@Override
	public User getUserByLogin(String login) {
		return userDao.getUserByLogin(login);
	}

	@Override
	public boolean checkLoginPassword(String login, String password) {
		User temp = getUserByLogin(login);
		if(temp!=null && temp.getPassword().equals(password))
			return true;
		return false;			
			
	}

	@Override
	public List<User> listInstructor(String mot) {
		return  userDao.listByName(mot);
		
	}

	@Override
	public List<User> listTrainees(long id) {
	
		
	TrainingSession	ts = trainingSessionDao.findById(id);
	ts.loadUsers();
	return ts.getTrainees();
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}
	
	

}
