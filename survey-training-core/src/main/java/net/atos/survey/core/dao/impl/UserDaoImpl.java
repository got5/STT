package net.atos.survey.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.User;

@Stateless(name="net.atos.survey.core.dao.UserDao")
public class UserDaoImpl extends DaoImpl<Long, User> implements UserDao {

	@Override
	public User getUserByLogin(String login) {
		return find("select u from User u where u.login=?1", login);
	}

	
	


	@Override
	public List<User> listByName(String mot) {
		String queryString;
		if (mot != null)
			if (!mot.equals("")) {
				mot = mot.toLowerCase() + "%";

				queryString = "select distinct t.instructor from TrainingSession t "
						+ "where (lower(t.instructor.name) like ?1 "
						+ "or lower(t.instructor.firstName) like ?1 )"
						+ "order by t.instructor.name";
				return list(null, null, queryString, mot);
			}
		queryString = "select distinct t.instructor from TrainingSession t "
				+ "order by t.instructor.name";
		return list(null, null, queryString);
	}
	
}
