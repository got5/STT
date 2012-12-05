package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.ResponseSurvey;

@Local
public interface ResponseSurveyDao extends Dao<Long, ResponseSurvey> {

}
