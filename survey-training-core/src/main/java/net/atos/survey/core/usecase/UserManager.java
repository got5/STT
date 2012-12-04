package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.User;


@Local
public interface UserManager {
	
	User getUserByLogin(String login);

}
