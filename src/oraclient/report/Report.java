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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Report {
    
    public Report(JTextArea area, JTable table) {
        excelReport(area, table);
        wordReport(area, table);
        pdfReport(area);
    }

    public void wordReport(JTextArea area, JTable table) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        //        run.setText("Hello, world!!!");
        run.setText(area.getText());
        try (FileOutputStream out = new FileOutputStream(new File("D:/hello.docx"))) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void excelReport(JTextArea area, JTable table) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        
        Row row = sheet.createRow((short)0);
        Cell cell = row.createCell(0);
        cell.setCellValue(1);
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue("hello, world");
        row.createCell(3).setCellValue(true);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        TableModel model = table.getModel();
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        Object[][] values = new Object[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                Object val = table.getValueAt(j, i);                
                values[i][j] = table.getValueAt(j, i);
                row.createCell(i, j).setCellValue(val.toString());
            }
        }
        System.out.println(Arrays.toString(values));
        try (FileOutputStream fileOut = new FileOutputStream("D:/hello.xlsx")) {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
        }
    }

    public void pdfReport(JTextArea area) {
        PDDocument pdf = new PDDocument();
        PDPage page = new PDPage();
        pdf.addPage(page);
        try {
            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream ctn = new PDPageContentStream(pdf, page);
            ctn.beginText();
            ctn.setFont(font, 12);
            ctn.drawString(area.getText());
            //            ctn.drawString("Hello, world!");
            ctn.endText();
            ctn.close();
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
