package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;

@Stateless(name="net.atos.survey.core.dao.UserDao")
public class UserDaoImpl extends DaoImpl<Long, User> implements UserDao {

	@Override
	public User getUserByLogin(String login) {
		return find("select u from User u where u.login=?1", login);
	}

	
	
	
}
