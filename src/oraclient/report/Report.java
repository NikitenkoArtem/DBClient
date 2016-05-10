package oraclient.report;

import java.io.IOException;

import javax.swing.JTextArea;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Report {
    private PDDocument pdf;

    public Report(JTextArea area) {
        pdf = new PDDocument();
        PDPage page = new PDPage();
        pdf.addPage(page);        
        try {
            PDPageContentStream ctn = new PDPageContentStream(pdf, page);
            PDFont font = PDType1Font.TIMES_ROMAN;
            ctn.beginText();
            ctn.setFont(font, 12);
            ctn.drawString(area.getText());
            ctn.endText();        
            pdf.save("D:/helloworld.pdf");
        } catch (IOException e) {
        } finally {
            if (pdf != null) {
                try {
                    pdf.close();
                } catch (IOException e) {
                }
            }
        }
    }
}