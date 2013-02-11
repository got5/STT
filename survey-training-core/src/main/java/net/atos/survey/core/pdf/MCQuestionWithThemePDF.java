package net.atos.survey.core.pdf;

import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.SimpleMCQResponse;
import net.atos.survey.core.entity.SimpleMCQuestion;

import static net.atos.survey.core.pdf.Constante.CHOICE_WITH_THEME_MARGIN;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class MCQuestionWithThemePDF {

	private static Font questionFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL, BaseColor.BLACK);
	private static Font elseFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL, BaseColor.BLACK);
	private static Font responseFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLDITALIC, BaseColor.BLACK);

	private PdfWriter writer;
	private Document document;

	private float[] coord;

	private SimpleMCQuestion question;
	private SimpleMCQResponse response;

	public MCQuestionWithThemePDF(PdfWriter writer, Document document,
			float[] coord, Question question, Response response)
			throws Exception {
		super();
		this.writer = writer;
		this.document = document;
		this.coord = coord;
		this.question = (SimpleMCQuestion) question;
		this.response = (SimpleMCQResponse) response;

		PdfContentByte under = writer.getDirectContentUnder();

		addMCQuestionWithTheme();
	}

	private void addMCQuestionWithTheme() throws DocumentException {
		addTitle();
		addChoice();
		addRemarque();

	}

	private void addRemarque() throws DocumentException {
		if (question.getTrigger() != null) {
			if (question.getValueOfChoice(response.getChoice()) <= question
					.getValueOfChoice(question.getTrigger())) {
				float[] coord = {
						this.coord[0] + (this.coord[2] - this.coord[0]) / 2 -CHOICE_WITH_THEME_MARGIN +25,
						this.coord[1], this.coord[2], this.coord[3] };

				printText(new Phrase(response.getAnswer(), elseFont), coord);
				if(response.getAnswer()!=null)
					System.out.println("******************###"+response.getAnswer().length());
			}
		}

	}

	private void addChoice() throws DocumentException {
		Phrase p = new Phrase(question.getValueOfChoice(response.getChoice())
				+ "", responseFont);

		
		float xl = coord[0] + ((coord[2] - coord[0]) / 2) - CHOICE_WITH_THEME_MARGIN;
		float[] coordTitle = { xl + 5, coord[1], xl + 15, coord[3] };

		PdfContentByte under = writer.getDirectContentUnder();
		under.saveState();
		under.setLineWidth(1);
		under.rectangle(xl, coordTitle[1] - 1, 15, 10);
		under.stroke();
		under.restoreState();

		printText(p, coordTitle);

	}

	private void addTitle() throws DocumentException {
		Phrase p = new Phrase(question.getTitle(), questionFont);

		float width = ((coord[2] - coord[0]) / 2) - CHOICE_WITH_THEME_MARGIN;
		float[] coordTitle = { coord[0], coord[1], coord[0] + width, coord[3] };
		printText(p, coordTitle);

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
