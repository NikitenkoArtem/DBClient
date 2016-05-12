package oraclient.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.TableColumn;

import javax.swing.table.TableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Report {
    private String filePath;
    private final String fileName = "report";
    private String fontName;
    private short fontSize = 8;
    
    public Report() {
//        excelReport(area, table);
//        wordReport(area, table);
//        pdfReport(area);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFontSize(short fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }
    
    private void makeReport(POIXMLDocument doc, String filePath) {
//        try (FileOutputStream out = new FileOutputStream(new File(filePath + fileName + ".docx"))) {
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wordReport(JTextArea area) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(fontName);
        run.setFontSize(fontSize);
        run.setText(area.getText());
    }

    public void wordReport(JTable table) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(fontName);
        run.setFontSize(fontSize);
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
        makeReport(doc, "D:/hello.docx");
    }
    
    public void excelReport(JTextArea area, String sheetName) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
    }
    
    public void excelReport(JTable table, String sheetName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName(fontName);
        font.setFontHeight(fontSize);
        style.setFont(font);
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
        makeReport((POIXMLDocument) workbook, "D:/hello.xlsx");
    }

    public void pdfReport(JTextArea area) {
        try (PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            pdf.addPage(page);
            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream ctn = new PDPageContentStream(pdf, page);
            ctn.beginText();
            ctn.setFont(font, fontSize);
            ctn.drawString(area.getText());
            //            ctn.drawString("Hello, world!");
            ctn.endText();
            ctn.close();
            pdf.save("D:/helloworld.pdf");
        } catch (IOException e) {
        }
    }
    
    public void pdfReport(JTable table) {
        
    }
}
