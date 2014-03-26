package net.atos.survey.gui.components.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.usecase.TrainingSessionManager;

import net.atos.survey.gui.pages.admin.PDFPage;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.Messages;
import javax.inject.Inject;

public class TrainingSessionComponent {

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Inject
	private Messages messages;

    @Property
	private TrainingSession ts;
	
	@Inject TrainingSessionManager trainingSessionManager;

    @InjectPage
    private PDFPage pdfPage;

    @Property
    private SimpleDateFormat dateFormat;


    @SetupRender
	public void load(){
        dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		ts = trainingSessionManager.findById(trainingSessionId);
	}
		
	@Property
	@Parameter
	private Long trainingSessionId;
	
	public boolean isInFuture(){
		
		boolean ret = false;
		Calendar now = new GregorianCalendar();
		Calendar from = ts.getDateS();
		if(from.getTimeInMillis() > now.getTimeInMillis() && from.get(Calendar.DAY_OF_MONTH)!=now.get(Calendar.DAY_OF_MONTH)){
			ret = true;
		}
		return ret;
	}
	


	@OnEvent(value=EventConstants.ACTION,component="pdfs")
	public Object generateAllPDF(){

        pdfPage.configureDocument(trainingSessionId);
        return pdfPage;

	}



}
