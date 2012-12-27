package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;


@Stateless(name="net.atos.survey.core.usecase.UserManager")
public class UserManagerImpl implements UserManager {

	@Inject
	UserDao userDao;
	
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
	
	

}
