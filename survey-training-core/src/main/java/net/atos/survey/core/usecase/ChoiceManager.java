package net.atos.survey.core.usecase;

import javax.ejb.Local;

import net.atos.survey.core.entity.Choice;


@Local
public interface ChoiceManager{

	Choice findById(Long id);
}
