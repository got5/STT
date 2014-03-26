package net.atos.survey.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Stateless
public class UserDaoImpl extends DaoImpl<Long, User> implements UserDao {

	@Override
	public User getUserByLogin(String login) {
		return find("select u from User u where u.login=?1", login);
	}

	
	


	@Override
	public List<User> listInstructorByName(String mot) {
		String queryString;
        String inChargeOf = "AND u.inChargeOfTrainings IS NOT EMPTY ";
		if (mot != null)
			if (!mot.equals("")) {
				mot = mot.toLowerCase() + "%";

				queryString = "select u from User u "
						+ "where (lower(u.name) like ?1 "
						+ "or lower(u.firstName) like ?1 ) "
                        + inChargeOf
						+ "order by u.name";
				return list(null, null, queryString, mot);
			}
		queryString = "select u from User u "
                + inChargeOf
				+ "order by u.name";
		return list(null, null, queryString);
	}

    @Override
    public List<User> listUserByName(String mot) {
        String queryString;
        if (mot != null)
            if (!mot.equals("")) {
                mot = mot.toLowerCase() + "%";

                queryString = "select distinct u from User u "
                        + "where (lower(u.name) like ?1 "
                        + "or lower(u.firstName) like ?1 )"
                        + "order by u.name";
                return list(null, null, queryString, mot);
            }
        queryString = "select distinct u from User u "
                + "order by u.name";
        return list(null, null, queryString);
    }


}
