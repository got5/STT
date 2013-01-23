package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Local
public interface ResponseSurveyDao extends Dao<Long, ResponseSurvey> {

	ResponseSurvey findByTrainingSessionAndByTrainee(Long ts, User trainee);

}
