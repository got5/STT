package net.atos.survey.gui.pages.admin;

import java.io.InputStream;

import net.atos.survey.core.usecase.PDFGeneratorManager;
import net.atos.survey.gui.data.PDFStreamResponse;
import net.atos.survey.gui.pages.Index;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;

import javax.inject.Inject;


public class PDFPage {

    @Inject
    private PDFGeneratorManager pdfGeneratorManager;

    @Persist(PersistenceConstants.FLASH)
    private Long trainingSessionId;
    @Persist(PersistenceConstants.FLASH)
    private Long traineeId;


    public Object onActivate() {
        // Create PDF
        if (trainingSessionId == null) {
            return Index.class;
        }
        InputStream is;
        String title;
        try {
            if (traineeId == null) {
                is = pdfGeneratorManager.buildAllPDF(trainingSessionId);
                title = pdfGeneratorManager.createTitleForSession(trainingSessionId);
            } else {
                is = pdfGeneratorManager.buildPDF(trainingSessionId, traineeId);
                title = pdfGeneratorManager.createTitle(trainingSessionId, traineeId);
            }
            if (is == null) {

            }


            return new PDFStreamResponse(is, title);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


    public void configureDocument(Long trainingSessionId, Long traineeId) {
        this.trainingSessionId = trainingSessionId;
        this.traineeId = traineeId;
    }

    public void configureDocument(Long trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }

}
