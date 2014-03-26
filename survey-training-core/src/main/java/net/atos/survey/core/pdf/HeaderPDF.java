package net.atos.survey.core.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.net.URL;

public class HeaderPDF {
	
	private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 13,
			Font.BOLD | Font.UNDERLINE, BaseColor.BLACK);
	private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 6,
			Font.BOLD, BaseColor.BLACK);
	
	private static Font headerFontUnderline = new Font(
			Font.FontFamily.HELVETICA, 5, Font.BOLD | Font.UNDERLINE,
			BaseColor.BLACK);

	private PdfWriter writer;
	private Document document;
	Paragraph preface;

	
	public HeaderPDF(PdfWriter writer, Document document) {
		super();
		this.writer = writer;
		this.document = document;
		
		preface = new Paragraph();
	}
	
	
	public void addTitle(String title){
		preface.add(new Paragraph(title, titleFont));
		preface.add(new Paragraph(" "));
	}
	
	public void addLogo(URL url) throws Exception{
		Image image = Image.getInstance(url);
		image.setAlignment(Image.RIGHT | Image.TEXTWRAP);
		image.scalePercent(10);
		document.add(image);
	}
	
	public void addRemarque(String text){
		Paragraph p = new Paragraph(text,headerFont);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		preface.add(p);
		
		
	}


	public void addRemarquePlus(String text,String plus) {
		Paragraph p = new Paragraph(text,headerFont);
		p.add(new Chunk(plus, headerFontUnderline));
		
		p.setAlignment(Paragraph.ALIGN_CENTER);
		preface.add(p);
	}
	
	public void configure() throws DocumentException{
		document.add(preface);
	}
	
	
	
	
	
}
