package net.atos.survey.core.dao;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.User;





@Local
public interface UserDao extends Dao<Long, User> {

	User getUserByLogin(String login);

	List<User> listByName(String mot);


	
}
