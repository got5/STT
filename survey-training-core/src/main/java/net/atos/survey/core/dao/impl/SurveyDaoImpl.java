package net.atos.survey.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.SurveyDao;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Survey;

@Stateless
public class SurveyDaoImpl extends DaoImpl<Long, Survey> implements SurveyDao {

	@Override
	public Survey findByTrainingSession(Long trainingSessionId) {
		String queryString="select ts.survey  from TrainingSession ts "
				+" where ts.id = ?1 ";

		
		return find(queryString, trainingSessionId);
	}

    @Override
    public Survey findOne() {

        String queryString = "select s from Survey s ";


        return find(queryString);
    }


}
