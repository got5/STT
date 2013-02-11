package net.atos.survey.core.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

public class ColumnSectionPDF {

	private static Font labelFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL, BaseColor.BLACK);
	private static Font textFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLDITALIC, BaseColor.BLACK);
	
	private PdfWriter writer;
	private Document document;

	private float [][] coord;
	
	private ColumnText ct;


	public ColumnSectionPDF(PdfWriter writer, Document document, float[][] coord) {
		this.writer = writer;
		this.document = document;
		this.coord=coord;
		 ct = new ColumnText(writer.getDirectContent());
		
		
	}
	
	public void addText(String label,String text){
		ct.addText(makePhrase(label, text));
		ct.addText(Chunk.NEWLINE);
		
	}
	
	public void addText(Phrase p){
		ct.addText(p);
	}
	
	
	
	public void configure() throws DocumentException{
		ct.setAlignment(Element.ALIGN_LEFT);

		ct.setLeading(0, 1.1f);
		ct.setFollowingIndent(27);

		int column = 0;
		int status = ColumnText.START_COLUMN;
		while (ColumnText.hasMoreText(status)) {

			ct.setSimpleColumn(coord[column][0], coord[column][1],
					coord[column][2], coord[column][3]);

			ct.setYLine(coord[column][3]);
			status = ct.go();
			column = Math.abs(column - 1);
		}
	}
	
	

	
	private Phrase makePhrase(String label, String text) {
		if ((text.length() + label.length()) > 65)
			text = text.substring(0, 65 - label.length());

		Phrase p = new Phrase();
		p.add(new Phrase(label, labelFont));
		p.add("  ");
		p.add(new Phrase(text, textFont));

		return p;
	}

	
}
