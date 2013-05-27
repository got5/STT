package net.atos.survey.gui.components;

import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.OResponse;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.usecase.SimpleMCQuestionManager;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import javax.inject.Inject;


@Import(stylesheet="context:static/css/question-component.css")
public class QuestionComponent {
	
	
	
	
	@Inject 
	SimpleMCQuestionManager simpleMCQuestionManager;

	@Property(read = false)
	@Parameter
	private Question question;
	
	@Parameter
	private Response response;
	
	@Parameter
	@Property
	private String mode;

	@Property
	private Choice choice;

	public SimpleMCQuestion getMCQuestion() {	
		//load all bc  categories are in lazy mode
		return (SimpleMCQuestion)question;	
	}

	public OQuestion getOQuestion() {
		
		return (OQuestion) question;
	}
	
	public OResponse getOresponse(){
		return (OResponse) response;
		
	}
	
	public SimpleMCQResponse getSimpleMCQResponse(){
		
		return (SimpleMCQResponse) response;
	}


	public boolean getIsMCQuestion() {
		return (question instanceof SimpleMCQuestion);
	}
	
	public boolean getMCQuestionHasElseClause(){
		
		return getMCQuestion().getElseClause()!=null;
	}
	
	public boolean getIsTrigger(){
		return getMCQuestion().isTrigger(choice);
	}
	
	

	
}
