package net.atos.survey.core.dao;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Training;





@Local
public interface TrainingDao extends Dao<Long, Training> {

	List<Training> listByName(String trainingName);


	
}
