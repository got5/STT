package net.atos.survey.gui.pages.admin;

import java.io.InputStream;

import net.atos.survey.core.usecase.PDFGeneratorManager;
import net.atos.survey.gui.data.PDFStreamResponse;
import net.atos.survey.gui.pages.Index;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import javax.inject.Inject;


public class PDFPage {
	
	@Inject PDFGeneratorManager pdfGeneratorManager;
	
	@Persist(PersistenceConstants.FLASH)
	private Long  trainingSessionId;
	@Persist(PersistenceConstants.FLASH)
	private Long traineeId;
	
	
	public Object onActivate(){
		 // Create PDF
		if (trainingSessionId == null || traineeId == null) {
			return Index.class;
		}
		InputStream is;
		try {
			is = pdfGeneratorManager.buildPDF(trainingSessionId,traineeId);
			String title = pdfGeneratorManager.createTitle();
			
			return new PDFStreamResponse(is,title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
       
	}
	
	
	public void configureDocument(Long trainingSessionId,Long traineeId){
		this.trainingSessionId=trainingSessionId;
		this.traineeId=traineeId;
	}

}
