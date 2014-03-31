package net.atos.survey.core.dao;

import java.util.List;

import javax.ejb.Local;

import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Survey;





@Local
public interface SurveyDao extends Dao<Long, Survey> {

	Survey findByTrainingSession(Long trainingSessionId);


    Survey findOne();

}
