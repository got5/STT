package net.atos.survey.core.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class FooterPDF {

	private PdfWriter writer;
	private Document document;

	public FooterPDF(PdfWriter writer, Document document) {
		super();
		this.writer = writer;
		this.document = document;
	}

	public void addFooter(String text) {
		PdfContentByte over = writer.getDirectContent();
		over.saveState();

		Rectangle pageSize = document.getPageSize();

		over.moveTo(pageSize.getBorderWidthLeft() + 30,
				pageSize.getBorderWidthBottom() + 30);
		over.lineTo(pageSize.getWidth() - 30,
				pageSize.getBorderWidthBottom() + 30);
		over.stroke();
		
		over.restoreState();

	}

}
