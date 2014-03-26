package net.atos.survey.core.usecase.impl;

import static net.atos.survey.core.pdf.Constante.QUESTION_SIZE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.NoTrainingSessionFoundException;
import net.atos.survey.core.exception.UserNotAnsweredToSurveyException;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.exception.UserNotInTrainingSessionException;
import net.atos.survey.core.pdf.CategoryPDF;
import net.atos.survey.core.pdf.ColumnSectionPDF;
import net.atos.survey.core.pdf.FooterPDF;
import net.atos.survey.core.pdf.HeaderPDF;
import net.atos.survey.core.pdf.VerticalLabelPDF;
import net.atos.survey.core.usecase.PDFGeneratorManager;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

@Stateless
public class PDFGeneratorManagerImpl implements PDFGeneratorManager {

    @Inject
    TrainingSessionManager trainingSessionManager;
    @Inject
    UserManager userManager;
    @Inject
    SurveyManager surveyManager;
    @Inject
    ResponseSurveyManager responseSurveyManager;


    public static final float[][] COLUMNS_VS = {{46, 700, 287, 750},
            {316, 700, 565, 750}};

    public InputStream buildPDF(Long trainingSessionId, Long traineeId)
            throws Exception {

        PdfWriter writer;
        Document document;

        TrainingSession ts;
        User trainee;
        ResponseSurvey responseSurvey;
        Survey survey;

        if (trainingSessionId == null || traineeId == null) {
            throw new NullPointerException("Page PDFGenerator non initialisé");
        }

        ts = trainingSessionManager.findById(trainingSessionId);
        trainee = userManager.findById(traineeId);

        if (ts == null)
            throw new NoTrainingSessionFoundException();
        if (trainee == null)
            throw new UserNotExistException();

        ts = trainingSessionManager.loadAll(trainingSessionId);
        if (!ts.getTrainees().contains(trainee))
            throw new UserNotInTrainingSessionException();

        responseSurvey = ts.getResponseSurvey(trainee);

        if (responseSurvey == null)
            throw new UserNotAnsweredToSurveyException();

        survey = surveyManager.loadAllR(ts.getSurvey());

        // step 1: creation of a document-object
        document = new Document(PageSize.A4, 20, 10, 20, 20);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            writer = PdfWriter.getInstance(document, baos);
            writer.setCompressionLevel(0);
            document.open();

            addTitlePage(writer, document);

            addContent(ts, trainee, responseSurvey, survey, writer, document);

            addFooter(writer, document);

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        // step 5: we close the document
        document.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;

    }

    @Override
    public InputStream buildAllPDF(Long trainingSessionId) throws Exception {
        TrainingSession ts = trainingSessionManager.findByIdWithTrainees(trainingSessionId);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // step 1
        Document document = new Document();
        // step 2
        PdfCopy copy = new PdfCopy(document, baos);
        // step 3
        document.open();
        // step 4
        PdfReader reader;
        int n;
        boolean atLeastOne = false;
        // loop over the documents you want to concatenate
        for (User u : ts.getTrainees()) {
            if (trainingSessionManager.alreadyAnsweredToSurvey(trainingSessionId, u.getId())) {
                atLeastOne = true;
                reader = new PdfReader(buildPDF(trainingSessionId, u.getId()));
                // loop over the pages in that document
                n = reader.getNumberOfPages();
                for (int page = 0; page < n; ) {
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }

                copy.freeReader(reader);
                reader.close();
            }
        }
        if(!atLeastOne){
            return null;
        }

        // step 5
        document.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;

    }

    private void addFooter(PdfWriter writer, Document document) {
        FooterPDF footer = new FooterPDF(writer, document);

        footer.addFooter("Atos Worldline");
    }

    private void addContent(TrainingSession ts, User trainee, ResponseSurvey responseSurvey, Survey survey, PdfWriter writer, Document document) throws Exception {

        addVousStage(ts, trainee, writer, document);

        CategoryPDF cPDF;
        float[] coord = {COLUMNS_VS[0][0], COLUMNS_VS[0][1], COLUMNS_VS[1][2],
                COLUMNS_VS[1][3]};

        for (Category c : survey.getCategories()) {

            int length = c.getName().length();
            int count = c.getQuestions().size()
                    + ((c.getRemarque() != null && !c.getRemarque().equals("")) ? 1
                    : 0);

            if (length > 2 * count)
                count = length / 2;

            coord = calcLayout(count, coord);

            cPDF = new CategoryPDF(writer, document, c, responseSurvey, coord);

        }
    }

    private float[] calcLayout(int count, float[] coord) {

        float y2 = coord[1] - 10;
        float y1 = y2 - count * QUESTION_SIZE;
        float[] ret = {coord[0], y1, coord[2], y2};

        return ret;
    }

    private void addTitlePage(PdfWriter writer, Document document) throws Exception {
        HeaderPDF header = new HeaderPDF(writer, document);
        header.addTitle("EVALUATION DE STAGE");
        header.addLogo(getClass().getClassLoader().getResource("atos-logo.jpg"));
        header.addRemarque("Vos remarques nous sont utiles pour adapter les formations à vos besoins. ");
        header.addRemarquePlus(
                "Merci de remplir ce questionnaire le plus précisément possible et de le retourner au service ",
                "Formation.");
        header.configure();
    }

    private void addVousStage(TrainingSession ts, User trainee, PdfWriter writer, Document document) throws Exception {

        createVSContent(ts, trainee, COLUMNS_VS, writer, document);
        new VerticalLabelPDF(writer, document, COLUMNS_VS[0])
                .addVerticalLabel("VOUS");
        new VerticalLabelPDF(writer, document, COLUMNS_VS[1])
                .addVerticalLabel("STAGE");
    }

    private void createVSContent(TrainingSession ts, User trainee, float[][] COLUMNS_VS, PdfWriter writer, Document document) throws DocumentException {

        ColumnSectionPDF twoC = new ColumnSectionPDF(writer, document,
                COLUMNS_VS);
        twoC.addText("Nom-Prénom: ",
                trainee.getName() + " " + trainee.getFirstName());
        twoC.addText(
                "Entité/BU/Dépt. ",
                trainee.getEntity() + "/" + trainee.getBu() + "/"
                        + trainee.getDept());
        twoC.addText("Fonction: ", "Assistant Etudes et Développement");
        twoC.addText("Titre: ", ts.getTraining().getName());
        twoC.addText("Dates: ",
                getDate(ts.getDateS()) + " à " + getDate(ts.getDateE()));
        twoC.addText("Formateur: ", ts.getInstructor().getName() + " "
                + ts.getInstructor().getFirstName());

        twoC.configure();
    }

    public String createTitle(Long tsId, Long traineeId) {

        TrainingSession ts = trainingSessionManager.findById(tsId);
        User trainee = userManager.findById(traineeId);

        String title = "";

        title += trainee.getName() + "-" + trainee.getFirstName() + "-"
                + ts.getTraining().getName();
        return title;

    }

    @Override
    public String createTitleForSession(Long tsId) {
        TrainingSession ts = trainingSessionManager.findById(tsId);
        String title = ts.getTraining().getName();
        title += "-";
        title += getDate(ts.getDateS());
        return title;
    }


    private String getDate(Calendar d) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(d.getTime());
    }

}
