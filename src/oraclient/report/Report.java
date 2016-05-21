package oraclient.report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;

import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.Encoding;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

    public void wordReport(JTextArea textArea, String filePath) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(FONTNAME);
        run.setFontSize(FONTSIZE);
        run.setText(textArea.getText());
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

    public void excelReport(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
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

    public void pdfReport(JTextArea textArea, String filePath) {
        //        char[] area = textArea.getText().toCharArray();
        //        StringBuilder text = new StringBuilder();
        //        Encoding encode = Encoding.getInstance(COSName.WIN_ANSI_ENCODING);
        //        for (int i = 0; i < area.length; i++) {
        //            Character c = area[i];
        //            int code = 0;
        //            if(Character.isWhitespace(c)){
        //                code = encode.getCode("space");
        //            }else{
        //                code = encode.getCode(encode.getNameFromCharacter(c));
        //            }
        //            text.appendCodePoint(code);
        //        }
        //        contentStream.drawString( text.toString() );
        //        String[] lines = textArea.getText().split("\\n");
        //        for(int i = 0 ; i< lines.length; i++)
        //                    printwriter.println( lines[i]);
        try (PDDocument pdf = new PDDocument()) {
            PDPage page = new PDPage();
            pdf.addPage(page);
            //            PDFont font = PDType1Font.TIMES_ROMAN;
            String text = textArea.getText();
            try (PDPageContentStream content = new PDPageContentStream(pdf, page)) {
                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, FONTSIZE);
                //                content.moveTextPositionByAmount(100, 700);
                content.showText(text);
                content.newLine();
                content.endText();
            }
            filePath = addExtension(filePath, ".pdf");
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
            content.setFont(font, FONTSIZE);
            int rowCount = table.getRowCount();
            int columnCount = table.getColumnCount();
            short length = 0;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    Object value = table.getValueAt(i, j);
                    Object column = table.getColumnName(i);
                    if (column != null) {
                        int len = column.toString().length();
                        if (length < len) {
                            length = (short) len;
                        }
                    }
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
//            float nextX = columnCount;
//            float nextY = y - rowCount * length;
            float nextY = y - rowCount * FONTSIZE;
            for (int i = 0; i <= columnCount; i++) {
                content.moveTo(tableX, tableY);
                content.lineTo(nextX, nextY);
                content.stroke();
                tableX += x + length;// + cellMargin / 2;
                nextX = tableX;
            }
//            tableX = x;
//            tableY = y;
////            nextX = x;
//            nextX = x * columnCount;// * length // + cellMargin;
//            nextY = y;
//            for (int i = 0; i <= rowCount; i++) {
//                content.moveTo(tableX, tableY);
//                content.lineTo(nextX, nextY);
//                content.stroke();
//                tableY -= FONTSIZE;// + cellMargin;
//                nextY = tableY;
//            }
            
            tableX = x;
            tableY = y;
            nextX = x;
            nextY = y - 10;
            for (int i = 0; i < columnCount; i++) {
                content.beginText();
                content.newLineAtOffset(nextX, nextY);
                Object value = table.getColumnName(i);
                if (value != null) {
                    content.showText(value.toString());
                }
                nextX += x + length + cellMargin;
                content.endText();
            }
            tableX = x;
            tableY = y;
            nextX = x;
            nextY = y - 10;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    content.beginText();
                    content.newLineAtOffset(nextX, nextY);
                    Object value = table.getValueAt(i, j);
                    if (value != null) {
                        content.showText(value.toString());
                    }
                    nextX += x + length + cellMargin;
//                    content.newLine();
                    content.endText();
                }
                nextY -= FONTSIZE;
                nextX = x;
            }
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
}
