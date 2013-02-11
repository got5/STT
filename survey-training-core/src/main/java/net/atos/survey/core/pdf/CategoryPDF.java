package net.atos.survey.core.pdf;

import java.util.Collection;

import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.entity.Question;
import net.atos.survey.core.entity.Response;
import net.atos.survey.core.entity.ResponseSurvey;
import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.entity.Theme;

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

public class CategoryPDF {

	private static Font remarqueFont = new Font(Font.FontFamily.HELVETICA, 7,
			Font.BOLD | Font.UNDERLINE, BaseColor.BLACK);

	private static Font themeFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLD | Font.UNDERLINE, BaseColor.BLACK);
	private static Font legendFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL, BaseColor.BLACK);

	private PdfWriter writer;
	private Document document;
	private Category category;
	private ResponseSurvey responseSurvey;
	private float[] coordCategory;
	private float[] current;
	private float height;
	// Catégorie de questions à choix multiples identiques. Les questions sont
	// aussi divisés par theme
	private boolean mcqWithTheme = false;

	public CategoryPDF(PdfWriter writer, Document document, Category c,
			ResponseSurvey rs, float[] coord) throws Exception {
		super();
		this.writer = writer;
		this.document = document;
		this.category = c;
		this.responseSurvey = rs;
		this.coordCategory = coord;
		this.current = coord.clone();

		this.height = coordCategory[3] - coordCategory[1];
		if (category.getRemarque() != null
				&& !category.getRemarque().equals(""))
			this.height -= 20; // remove space remarque
		this.height /= category.getQuestions().size();

		for (Question q : c.getQuestions()) {
			if (q.getTheme() != null) {
				mcqWithTheme = true;
				break;
			}
		}

		addLabel(c.getName());

		addRemarque();

		if (mcqWithTheme) {
			addLegend();
		}

		addQuestions();
	}

	private void addLegend() throws DocumentException {
		
		
		float x = (coordCategory[2] - coordCategory[0]) / 2 - CHOICE_WITH_THEME_MARGIN +20;
		float[] coord = { x + coordCategory[0] + 5, coordCategory[3] - 15,
				coordCategory[2], coordCategory[3] - 5 };

		PdfContentByte canvas = writer.getDirectContentUnder();

		canvas.saveState();
		canvas.setLineWidth(1);
		canvas.rectangle(x + coordCategory[0], coordCategory[3] - 20, coordCategory[2]-(x+40), 15);
		canvas.stroke();
		canvas.restoreState();

		String choicesStr = "";
		SimpleMCQuestion mcq = (SimpleMCQuestion) category.getQuestions()
				.get(0);
		Collection<Choice> choices = mcq.getChoices();

		for (Choice c : choices) {
			choicesStr += "         " + c.getName() + "  =  "
					+ mcq.getValueOfChoice(c);
		}

		Phrase p = new Phrase(choicesStr, legendFont);
		
		printText(p, coord);

	}

	public void addLabel(String text) throws Exception {
		VerticalLabelPDF vlP = new VerticalLabelPDF(writer, document,
				coordCategory);

		vlP.addVerticalLabel(category.getName());
	}

	private void addRemarque() throws DocumentException {
		String remarque = category.getRemarque();

		if (remarque != null && !remarque.equals("")) {

			float[] coord = { current[0], current[1], current[2],
					current[3] - 5 };

			printText(new Phrase(remarque, remarqueFont), coord);

			current[3] = current[3] - 20;
		} else {
			current[3] = current[3] - 5;
		}
	}

	private void addTheme(String theme) throws DocumentException {
		float[] coord = { current[0], current[1], current[2], current[3] - 5 };

		printText(new Phrase(theme, themeFont), coord);

		current[3] = current[3] - 20;

	}

	private void addQuestions() throws Exception {
		// vertical title

		Theme t = null;
		Response r = null;

		for (Question q : category.getQuestions()) {

			r = responseSurvey.getResponses().get(q);

			if (q.getTheme() != null) {
				if (!q.getTheme().equals(t)) {

					addTheme(q.getTheme().getName());
					t = q.getTheme();
				}
				float[] coordQ = calcCoordQuestion(q);
				MCQuestionWithThemePDF qp = new MCQuestionWithThemePDF(writer,
						document, coordQ, q, r);
			} else {
				float[] coordQ = calcCoordQuestion(q);
				QuestionPDF qp = new QuestionPDF(writer, document, coordQ, q, r);
			}
		}

	}

	private float[] calcCoordQuestion(Question q) {
		float nbT = countNbTheme();
		float nbQ = category.getQuestions().size();
		float ratio = nbT / nbQ;
		float y = height;

		y -= 17 * ratio;
		y -= 2f;

		float xl = current[0];
		float yl = current[3] - y;
		float xp = current[2];
		float yp = current[3] - 4f;

		current[3] -= y;

		float[] ret = { xl, yl, xp, yp };
		return ret;

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

	private float countNbTheme() {
		int nb = 0;
		Theme t = null;
		for (Question q : category.getQuestions()) {
			Theme qt = q.getTheme();
			if (qt != null && !qt.getName().equals("") && !qt.equals(t)) {
				t = qt;
				nb++;
			}
		}
		return nb;
	}

}
