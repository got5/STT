package net.atos.survey.core.usecase;

import java.io.InputStream;

import javax.ejb.Local;

@Local
public interface PDFGeneratorManager {
	
	public  InputStream buildPDF(Long trainingSessionId,Long traineeId) throws Exception ;

    String createTitle();

}
