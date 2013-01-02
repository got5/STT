package net.atos.survey.core.usecase;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.User;


@Local
public interface UserManager {
	
	User getUserByLogin(String login);
	
	boolean checkLoginPassword(String login,String password);

	List<String> listInstructor(String mot);

}
