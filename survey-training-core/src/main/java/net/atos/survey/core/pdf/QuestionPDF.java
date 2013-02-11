package net.atos.survey.core.pdf;

import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.SimpleMCQuestion;

import static net.atos.survey.core.pdf.Constante.CHOICE_SIZE;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class QuestionPDF {

	private static Font questionFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL, BaseColor.BLACK);
	private static Font elseFont = new Font(Font.FontFamily.HELVETICA, 7,
			Font.NORMAL, BaseColor.BLACK);
	private static Font responseFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLDITALIC, BaseColor.BLACK);

	private PdfWriter writer;
	private Document document;

	private float[] coordLeft;
	private float[] coordRight;

	private Question question;
	private Response response;

	public QuestionPDF(PdfWriter writer, Document document, float[] coord,
			Question question, Response response) throws Exception {
		super();
		this.writer = writer;
		this.document = document;
		this.coordLeft = coord;
		this.coordRight = coord;
		this.question = question;
		this.response = response;

		PdfContentByte under = writer.getDirectContentUnder();

		
		addQuestion();
	}

	private void addQuestion() throws Exception {

		if (question instanceof OQuestion) {
			addOQuestion();
		} else if (question instanceof SimpleMCQuestion) {
			addMCQuestion();
		}

	}

	private void addMCQuestion() throws DocumentException {
		SimpleMCQuestion mcq = (SimpleMCQuestion) question;
		SimpleMCQResponse mcqr = (SimpleMCQResponse) response;
		addTitle();
		addChoice();
		if (mcq.getTrigger() != null && mcqr.getChoice()!=null) {
			if (mcq.getValueOfChoice(mcqr.getChoice()) <= mcq
					.getValueOfChoice(mcq.getTrigger())) {
				addAnswer();
			}
		}


	}

	private void addTitle() throws DocumentException {
		BaseFont bf = questionFont.getCalculatedBaseFont(true);
		String titleStr = question.getTitle();
		float fontSize = questionFont.getSize();
		
		float sizeTitle = bf.getWidthPoint(titleStr, fontSize);
		
		
		System.out.println("sizeTitle ******************* "+sizeTitle);
		
		Phrase p = new Phrase(question.getTitle(), questionFont);
		float width = (coordLeft[2] - coordLeft[0]) / 2;
		float[] coordTitle = { coordLeft[0], coordLeft[1], coordLeft[0] + width, coordLeft[3] };
		printText(p, coordTitle); 
		System.out.println("width "+width);
		
		if(sizeTitle>=width){
			coordLeft[3]=coordLeft[3]-10;
		}
	}
	
	private void addChoice() throws DocumentException {
		SimpleMCQResponse mcqr = (SimpleMCQResponse) response;
		Phrase p = new Phrase(mcqr.getChoice().getName(),responseFont);

		float width = (coordRight[2] - coordRight[0]) / 2;
		float[] coordTitle = { coordRight[0]+width, coordRight[1], coordRight[0]+width+CHOICE_SIZE, coordRight[3] };

	
		printText(p, coordTitle);

	}
	
	private void addElse() throws DocumentException{
		float width = (coordLeft[2] - coordLeft[0]) / 2;
		float[] coordElse = { coordLeft[0]+width+CHOICE_SIZE, coordLeft[1], coordLeft[2], coordLeft[3] };
		
		Phrase p = new Phrase(response.getAnswer(),elseFont);
		printText(p, coordElse);
	}
	
	private void addAnswer() throws DocumentException{
		
		
		Phrase p = new Phrase(response.getAnswer(),responseFont );
		float[] answerCoord = { coordLeft[0], coordLeft[1], coordLeft[2], coordLeft[3]-10 };
		printText(p, answerCoord);
	}


	private void addOQuestion() throws DocumentException {
		printText(new Phrase(question.getTitle(), questionFont), coordLeft);
		addAnswer();
	}

	private void printText(Phrase p, float[] coord) throws DocumentException {

		PdfContentByte canvas = writer.getDirectContent();
		canvas.saveState();
		ColumnText ct = new ColumnText(canvas);

		ct.addText(p);
		ct.setAlignment(Element.ALIGN_LEFT);

		ct.setLeading(0, 1.1f);

		ct.setSimpleColumn(coord[0], coord[1], coord[2], coord[3]);

		ct.go();
		canvas.restoreState();
	}
}
