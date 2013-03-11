package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.entity.Category;

@Stateless
public class CategoryDaoImpl extends DaoImpl<Long, Category> implements CategoryDao {

	@Override
	public Category findByName(String name) {
		return find("select c from Category c where c.name=?1", name);
		
	}

	
	
	
}
