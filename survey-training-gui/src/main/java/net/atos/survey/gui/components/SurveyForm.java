package net.atos.survey.gui.components;

import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.Theme;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.CategoryManager;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SurveyForm {

	@SessionState
	private User user;

	@Inject
	private CategoryManager categoryManager;

	@Property
	private Question question;

	@Property(read = false)
	private Category category;

	@Parameter
	private Survey survey;

	@Parameter
	@Property
	private ResponseSurvey responseSurvey;

	/*
	 * @Component(publishParameters="responseSurvey") private QuestionComponent
	 * questionComponent;
	 */

	@Property
	private int indexC = 0;

	@Property
	private int indexQ = 0;

	@Property
	private Theme previousTheme;

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Category getCategory() {
		this.category = categoryManager.loadAll(this.category);
		return category;
	}

	public boolean getAfficherTheme() {
		boolean ret = false;
		Theme theme = question.getTheme();
		if (theme != null && !theme.equals(previousTheme)) {
			ret = true;
			previousTheme = theme;
		}
		return ret;
	}


}
