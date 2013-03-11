package net.atos.survey.core.usecase.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.usecase.CategoryManager;

@Stateless
public class CategoryManagerImpl implements CategoryManager {
	
	@Inject 
	CategoryDao categoryDao;

	@Override
	public Category loadAll(Category category) {
		category = categoryDao.findById(category.getId());
		category.loadQuestions();
		return category;
	}

}
