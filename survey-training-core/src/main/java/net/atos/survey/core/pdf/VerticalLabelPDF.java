package net.atos.survey.core.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class VerticalLabelPDF {
	
	private static final Font verticalFont = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD,
			BaseColor.BLACK);

	private PdfWriter writer;
	private Document document;

    private	float xl;
	private float yl;
	private float xp;
	private float yp;

	public VerticalLabelPDF(PdfWriter writer, Document document, float[] coord) {
		this.writer = writer;
		this.document = document;

		xl = coord[0] - 23f;
		yl = coord[1] + 5f;
		xp = coord[0] - 10f;
		yp = coord[3] - 6.5f;
	}

	public void addVerticalLabel(String text) throws Exception {

		float length = text.length();
		
		float extraSpace = (yp - yl) - length * 7.7f;

		

		PdfContentByte canvas = writer.getDirectContent();
		canvas.saveState();
		ColumnText ct = new ColumnText(canvas);
		
		ct.setAlignment(Element.ALIGN_CENTER);

		char[] message = text.toCharArray();
		Paragraph p;
		for (char c : message) {
			p = new Paragraph(c+"",verticalFont);
			p.setAlignment(Element.ALIGN_CENTER);
			ct.addText(p);
			ct.addText(Chunk.NEWLINE);
		}
		
		ct.setLeading(0, 1f);

		int status = ColumnText.START_COLUMN;
		while (ColumnText.hasMoreText(status)) {
			ct.setSimpleColumn(xl, yl - extraSpace / 2, xp, yp - extraSpace / 2);
			ct.setYLine(yp - extraSpace / 2);
			status = ct.go();
		}

		fillFont();

		canvas.restoreState();
	}

	private void fillFont() {
		PdfContentByte under = writer.getDirectContentUnder();

		under.saveState();
		under.setRGBColorFill(0xC5, 0xC5, 0xC5);
		under.setLineWidth(1);
		under.rectangle(xl, yl, (xp - xl), (yp - yl));
		under.fillStroke();
		under.restoreState();

	}

}
