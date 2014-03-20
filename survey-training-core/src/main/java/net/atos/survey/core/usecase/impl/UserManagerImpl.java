package net.atos.survey.core.usecase.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.UserManager;

@Stateless
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
		return userDao.listInstructorByName(mot);

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
			login += firstname.substring(index, index + 1);
			index++;
		}
		newUser.setLogin(newUser.getName());
		newUser.setPassword(newUser.getFirstName());
		return userDao.save(newUser);

	}

    @Override
    public Long countUser() {
        return userDao.countLister();
    }

    @Override
    public List<User> listTrainees(String mot) {
        return userDao.listUserByName(mot);
    }
}
