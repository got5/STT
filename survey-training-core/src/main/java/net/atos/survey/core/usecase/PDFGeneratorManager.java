package net.atos.survey.core.usecase;

import java.io.InputStream;

import javax.ejb.Local;

@Local
public interface PDFGeneratorManager {

    InputStream buildPDF(Long trainingSessionId, Long traineeId) throws Exception;

    InputStream buildAllPDF(Long trainingSessionId) throws Exception;

    String createTitle(Long tsId, Long traineeId);

    String createTitleForSession(Long tsId);

}
