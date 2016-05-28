package dbclient.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;

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
    private final short fontSize = 14;

    public Report() {
    }

    private void makeReport(POIXMLDocument doc, String filePath) {
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wordReport(JTable table, String filePath) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(FONTNAME);
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
        filePath = addExtension(filePath, ".docx");
        makeReport(doc, filePath);
    }

    public void excelReport(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        String sheetName = "report";
        Sheet sheet = workbook.createSheet(sheetName);
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
                Object value = table.getValueAt(i, j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }
        filePath = addExtension(filePath, ".xlsx");
        makeReport((POIXMLDocument) workbook, filePath);
    }

    public void pdfReport(JTable table, String filePath) {
        try (PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            pdf.addPage(page);
            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream content = new PDPageContentStream(pdf, page);
            int rowCount = table.getRowCount();
            int columnCount = table.getColumnCount();
            short length = 0;
            for (int i = 0; i < rowCount; i++) {
                if (i == 0) {
                    Object column = table.getColumnName(i);
                    if (column != null) {
                        short columnLength = (short) column.toString().length();
                        if (length < columnLength) {
                            length = columnLength;
                        }
                    }
                }
                for (int j = 0; j < columnCount; j++) {
                    Object value = table.getValueAt(i, j);
                    if (value != null) {
                        short len = (short) value.toString().length();
                        if (length < len) {
                            length = len;
                        }
                    }
                }
            }
            final float x = 100;
            final float y = 700;
            final float cellMargin = 10;
            float tableX = x;
            float tableY = y;
            float nextX = x;
            float nextY = y - rowCount * fontSize;
            float endX = 0;
            for (int i = 0; i <= columnCount; i++) {
                content.moveTo(tableX, tableY);
                content.lineTo(nextX, nextY);
                content.stroke();
                endX = nextX;
                tableX += x + length;
                nextX = tableX;
            }
            tableX = x;
            nextX = x;
            tableY = y;
            nextY = y - cellMargin;
            for (int i = 0; i < rowCount; i++) {
                if (i == 0) {
                    line(content, tableX, y, endX, y);
                    for (int column = 0; column < columnCount; column++) {
                        content.beginText();
                        content.newLineAtOffset(nextX, nextY);
                        Object value = table.getColumnName(column);
                        if (value != null) {
                            content.setFont(font, 10);
                            content.showText(value.toString());
                        }
                        nextX += x + length + cellMargin;
                        content.endText();
                        line(content, x, nextY + cellMargin, endX, nextY + cellMargin);
                    }
                    content.setFont(font, fontSize);
                }
                for (int j = 0; j < columnCount; j++) {
                    content.beginText();
                    content.newLineAtOffset(nextX, nextY);
                    Object value = table.getValueAt(i, j);
                    if (value != null) {
                        content.showText(value.toString());
                    }
                    nextX += x + length + cellMargin;
                    content.endText();
                    line(content, x, nextY + cellMargin, endX, nextY + cellMargin);
                }
                nextY -= fontSize;
                nextX = x;
            }
            nextY = y - rowCount * fontSize;
            line(content, x, nextY, endX, nextY);
            content.close();
            filePath = addExtension(filePath, ".pdf");
            pdf.save(filePath);
        } catch (IOException e) {
        }
    }

    private String addExtension(String filePath, final String extension) {
        if (!filePath.endsWith(extension)) {
            filePath = filePath + extension;
        }
        return filePath;
    }

    private void line(PDPageContentStream content, float moveX, float moveY, float lineX,
                      float lineY) throws IOException {
        content.moveTo(moveX, moveY);
        content.lineTo(lineX, lineY);
        content.stroke();
    }
}
