package net.atos.survey.gui.components;

import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Survey;



public class SurveyForm {
	
	private Question question;
	
	private Survey survey;
	
	public Survey getSurvey(){
		return survey;
	}
	
	public void setSurvey(Survey survey){
		this.survey=survey;
		
		question = survey.getQuestions().get(1);
		
		
	}
	
	public Question getQuestion(){
		return question;
	}
	
	 
	
	

	
	
	

}
