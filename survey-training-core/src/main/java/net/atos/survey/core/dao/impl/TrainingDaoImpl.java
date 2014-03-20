package net.atos.survey.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.User;

@Stateless
public class TrainingDaoImpl extends DaoImpl<Long, Training> implements TrainingDao {

	@Override
	public List<Training> listByName(String mot) {
		String queryString = "";

		if (mot != null)
			if (!mot.equals("")) {
				mot = mot.toLowerCase() + "%";

				queryString = "select t from Training t "
						
						+ " where lower(t.name) like ?1 " + " order by t.name ";
				return list(null, null, queryString, mot);
			}
		queryString = "select t from Training t " + "order by t.name";
		return list(null, null, queryString);
	}

	@Override
	public Training findByName(String mot) throws Exception{
		if (mot != null)
			if (!mot.equals("")) {
				mot = mot.toLowerCase();

				String queryString = "select t from Training t "
						
						+ " where lower(t.name) like ?1 ";
				return find(queryString, mot);
			}
		return null;
	}

    @Override
    public List<Training> listManagingTraining(String trainingName, User user) {
        String queryString = "";
        List<Training> trainings = user.getInChargeOfTrainings();
        List<Long> trainingsId = new ArrayList<Long>();
        for(Training t: trainings){
            trainingsId.add(t.getId());
        }


        String subString = "WHERE tr.id IN ?1 ";
        if (trainingName != null)
            if (!trainingName.equals("")) {
                trainingName = trainingName.toLowerCase() + "%";


                queryString = "SELECT tr FROM Training tr "
                        + subString
                        + "AND lower(tr.name) like ?2 "
                        + "ORDER BY tr.name ";
                return list(null, null, queryString, trainingsId,trainingName);
            }
        queryString = "SELECT tr FROM Training tr "
                + subString
                + "ORDER BY tr.name";
        return list(null, null, queryString,trainingsId);
    }


}
