package net.atos.survey.gui.components;

import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Survey;

import org.apache.tapestry5.annotations.Property;

public class SurveyForm {

	private Question previousQuestion;

	@Property
	private Question question;

	private Survey survey;

	@Property
	private int index = 0;

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public boolean getCheckNewCategory() {
		boolean ret = true;

		if (previousQuestion != null
				&& question.getCategory()
						.equals(previousQuestion.getCategory()))
			ret = false;

		previousQuestion = question;
		return ret;
	}
	
	public boolean getEndCategory(){
		boolean ret = getCheckNewCategory();
		if(previousQuestion==null)
			ret=false;
		return ret;
	}

}
