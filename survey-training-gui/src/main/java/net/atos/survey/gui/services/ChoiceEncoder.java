package net.atos.survey.gui.services;

import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.usecase.ChoiceManager;

import org.apache.tapestry5.ValueEncoder;

public class ChoiceEncoder implements ValueEncoder<Choice> {

	ChoiceManager choiceManager;

	public ChoiceEncoder(ChoiceManager choiceManager) {
		this.choiceManager = choiceManager;
	}

	@Override
	public String toClient(Choice choice) {
		if (choice == null)
			return "";
		return String.valueOf(choice.getId());
	}

	@Override
	public Choice toValue(String clientValue) {
		if(clientValue ==null)
			return null;
		Long id = Long.valueOf(clientValue);
		return choiceManager.findById(id);
	}

}
