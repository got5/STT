package net.atos.survey.core.usecase;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;


@Local
public interface UserManager {
	
	User getUserByLogin(String login);
	
	User findById(Long id);
	
	boolean checkLoginPassword(String login,String password);

	List<User> listInstructor(String mot);

	List<User> listTrainees(Long id);
	
	List<Training> loadInChargeOf(Long id);

	User register(User newUser);
	
	
	

}
