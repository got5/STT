package net.atos.survey.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.entity.Training;

@Stateless(name="net.atos.survey.core.dao.TrainingDao")
public class TrainingDaoImpl extends DaoImpl<Long, Training> implements TrainingDao {

	@Override
	public List<Training> listNameByName(String mot) {
		String queryString = "";

		if (mot != null)
			if (!mot.equals("")) {
				mot = mot.toLowerCase() + "%";

				queryString = "select t from Training t "
						
						+ " and lower(t.name) like ?1 " + " order by c.nom ";
				return list(null, null, queryString, mot);
			}
		queryString = "select t from Training t " + "order by t.name";
		return list(null, null, queryString);
	}

	
	
	
}
