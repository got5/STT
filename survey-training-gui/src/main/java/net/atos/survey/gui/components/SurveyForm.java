package net.atos.survey.gui.components;

import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.Theme;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.usecase.CategoryManager;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import javax.inject.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


public class SurveyForm {

	@SessionState
	private User user;

	@Inject
	@Path("context:static/js/handleForm.js")
	private Asset handleFormJs;
	
	@Inject
	@Path("context:static/css/surveyForm1.css")
	private Asset stylesheet1;
	
	@Inject
	private CategoryManager categoryManager;

	@Property
	private Question question;

	@Property(read = false)
	private Category category;

	@Parameter
	@Property
	private Survey survey;

	@Parameter
	@Property
	private ResponseSurvey responseSurvey;

	@Component
	private Form form;

	@Property
	private int indexC = 0;

	@Property
	private int indexQ = 0;

	@Property
	private Theme previousTheme;

	@Property
	@Parameter
	private String mode;
	
	@Inject
	private JavaScriptSupport js;

	public Category getCategory() {
		return category;
	}

	public Response getResponse() {
		return responseSurvey.getResponses().get(question);
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
	
	@SetupRender
	public void setup(){
		if(mode==null){
			js.importJavaScriptLibrary(handleFormJs);
		}
	}
	
	@AfterRender
	public void after(){
		if(mode==null){
			js.addScript("disableForm('%s')",form.getClientId());
			
		}
		
		js.importStylesheet(stylesheet1);
		
	}

	@OnEvent(value = EventConstants.VALIDATE, component = "form")
	public void validateForm() {
		if (mode.equals("WRITE")) {
			for (Response r : responseSurvey.getResponses().values()) {
				if (r instanceof SimpleMCQResponse
						&& ((SimpleMCQResponse) r).getChoice() == null) {
					form.recordError("Veuillez répondre à toutes les questions à choix multiples.");
					break;
				}
			}
		}
	}

}
