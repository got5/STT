package net.atos.survey.core.usecase.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;


@Stateless(name="net.atos.survey.core.usecase.UserManager")
public class UserManagerImpl implements UserManager {

	@EJB
	UserDao userDao;
	
	@Override
	public User getUserByLogin(String login) {
		return userDao.getUserByLogin(login);
	}
	
	

}
