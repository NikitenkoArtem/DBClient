package oraclient.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Report {
    private final String FONTNAME = "Times New Roman";
    private final short FONTSIZE = 14;
    private String sheetName = "report";

    public Report() {
    }

//    public void setFontSize(short fontSize) {
//        this.fontSize = fontSize;
//    }
//
//    public int getFontSize() {
//        return fontSize;
//    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    private void makeReport(POIXMLDocument doc, String filePath) {
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wordReport(JTextArea area, String filePath) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(FONTNAME);
        run.setFontSize(FONTSIZE);
        run.setText(area.getText());
        filePath = addExtension(filePath, ".docx");
        makeReport(doc, filePath);
    }

    public void wordReport(JTable table, String filePath) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(FONTNAME);
        run.setFontSize(FONTSIZE);
        XWPFTable wordTable = doc.createTable();        
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        XWPFTableRow tableHeader = wordTable.getRow(0);
        for (int i = 0; i < columnCount; i++) {            
            if (i == 0) {
                tableHeader.getCell(i).setText(table.getColumnName(i));
                continue;
            }
            tableHeader.addNewTableCell().setText(table.getColumnName(i));
        }
        for (int i = 0; i < rowCount; i++) {
            XWPFTableRow row = wordTable.createRow();
            for (int j = 0; j < columnCount; j++) {
                Object val = table.getValueAt(i, j);
                if (val != null) {
                    row.getCell(j).setText(val.toString());
                } else {
                    row.getCell(j).setText("");
                }
            }
        }
        filePath = addExtension(filePath, ".docx");
        makeReport(doc, filePath);
    }
    
    public void excelReport(JTextArea area, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        filePath = addExtension(filePath, ".xlsx");
        makeReport((POIXMLDocument) workbook, filePath);
    }
    
    public void excelReport(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
////        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        font.setFontName(FONTNAME);
//        font.setFontHeight(FONTSIZE);
//        style.setFont(font);
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        Row tableHeader = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {            
            Cell cell = tableHeader.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }
        int firstRow = sheet.getFirstRowNum();
        for (int i = 0; i < rowCount; i++) {            
            Row row = sheet.createRow(firstRow + i + 1);
            for (int j = 0; j < columnCount; j++) {                
                Cell cell = row.createCell(j);
                Object val = table.getValueAt(i, j);
                if (val != null) {
                    cell.setCellValue(val.toString());
                }
            }
        }
        filePath = addExtension(filePath, ".xlsx");
        makeReport((POIXMLDocument) workbook, filePath);
    }

    public void pdfReport(JTextArea area, String filePath) {
        try (PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            pdf.addPage(page);            
            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream content = new PDPageContentStream(pdf, page);
            content.beginText();
            content.setFont(font, FONTSIZE);
            content.showText(area.getText());
            //            ctn.drawString("Hello, world!");
            content.endText();
            content.close();
            pdf.save(filePath);            
        } catch (IOException e) {
        }
    }
    
    public void pdfReport(JTable table, String filePath) {
        try (PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            pdf.addPage(page);            
            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream content = new PDPageContentStream(pdf, page);
            content.beginText();
            content.setFont(font, FONTSIZE);
//        final int rows = table.getRowCount();
//        final int cols = table.getColumnCount();
//        final float rowHeight = 20f;
//        final float tableWidth = page.getCropBox().getWidth() - margin - margin;
//        final float tableHeight = rowHeight * rows;
//        final float colWidth = tableWidth/(float)cols;
//        final float cellMargin=5f;

        //draw the rows
//        float nexty = y ;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                content.moveTo(0, 0);
                content.lineTo(0, 0);
                content.stroke();
            }
//            content.drawLine(margin, nexty, margin+tableWidth, nexty);
//            nexty-= rowHeight;
        }

        //draw the columns
//        float nextx = margin;
//        for (int i = 0; i < table.getColumnCount(); i++) {
            
//            content.drawLine(nextx, y, nextx, y-tableHeight);
//            nextx += colWidth;
//        }

        //now add the text
        content.setFont( PDType1Font.HELVETICA_BOLD , 12 );

//        float textx = margin+cellMargin;
//        float texty = y-15;
//        for(int i = 0; i < content.length; i++){
//            for(int j = 0 ; j < content[i].length; j++){
        for(int i = 0; i < table.getRowCount(); i++){
            for(int j = 0; j < table.getColumnCount(); j++){
//                String text = content[i][j];
                content.beginText();
//                content.moveTextPositionByAmount(textx,texty);
//                content.drawString(text);
                content.endText();
//                textx += colWidth;
            }
//            texty-=rowHeight;
//            textx = margin+cellMargin;
        }
        } catch (IOException e) {
        }
    }

    private String addExtension(String filePath, final String extension) {
        if (!filePath.endsWith(extension)) {
            filePath = filePath + extension;
        }
        return filePath;
    }
}
