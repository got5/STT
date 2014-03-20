package net.atos.survey.core.dao;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;


@Local
public interface TrainingDao extends Dao<Long, Training> {

	List<Training> listByName(String trainingName);

	Training findByName(String string) throws Exception;


    List<Training> listManagingTraining(String trainingName, User user);
}
