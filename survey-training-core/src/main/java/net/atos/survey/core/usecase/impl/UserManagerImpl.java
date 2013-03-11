package net.atos.survey.core.usecase.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.annotation.User1;
import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;

@Stateless
@User1
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
		if (temp != null && temp.getPassword().equals(password))
			return true;
		return false;

	}

	@Override
	public List<User> listInstructor(String mot) {
		return userDao.listByName(mot);

	}

	@Override
	public List<User> listTrainees(Long id) {
		TrainingSession ts = trainingSessionDao.findById(id);
		ts.loadUsers();
		return ts.getTrainees();
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public List<Training> loadInChargeOf(Long id) {
		User u = findById(id);
		u.getCountOfInChargeOfTraining();
		return u.getInChargeOfTrainings();

	}

	@Override
	public User register(User newUser) {
		String login = newUser.getName().toLowerCase();
		String firstname = newUser.getFirstName().toLowerCase();
		int index = 0;

		while (userDao.getUserByLogin(login) != null) {
			System.out.println("********" + userDao.getUserByLogin(login));
			login += firstname.substring(index, index + 1);
			System.out.println("******" + login + " " + index);
			index++;
		}
		String password = "azerty123";
		newUser.setLogin(login);
		newUser.setPassword(password);
		return userDao.save(newUser);

	}

}
