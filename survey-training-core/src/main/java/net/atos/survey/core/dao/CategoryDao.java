package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.Category;





@Local
public interface CategoryDao extends Dao<Long, Category> {

	Category findByName(String name);
	
}
