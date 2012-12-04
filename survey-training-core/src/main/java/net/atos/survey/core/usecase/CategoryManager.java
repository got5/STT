package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.Category;


@Local
public interface CategoryManager{

	Category loadAll(Category category);
}
