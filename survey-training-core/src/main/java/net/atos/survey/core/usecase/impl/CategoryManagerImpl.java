package net.atos.survey.core.usecase.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.usecase.CategoryManager;

@Stateless(name="net.atos.survey.core.usecase.CategoryManager")
public class CategoryManagerImpl implements CategoryManager {
	
	@EJB 
	CategoryDao categoryDao;

	@Override
	public Category loadAll(Category category) {
		category = categoryDao.findById(category.getId());
		category.loadQuestions();
		return category;
	}

}
