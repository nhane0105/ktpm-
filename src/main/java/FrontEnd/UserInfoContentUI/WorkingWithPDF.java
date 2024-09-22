package FrontEnd.UserInfoContentUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class WorkingWithPDF {
    // Sample data
    ArrayList<String> header = new ArrayList<>(Arrays.asList(
            "Tên NV:",
            "Mã NV:",
            "Giới Tính:",
            "Ngày Sinh:",
            "SĐT:",
            "Dân Tộc:",
            "Tôn Giáo:",
            "Quốc Tịch:",
            "Chuyên Môn:",
            "Bằng Cấp:",
            "Chức Vụ:",
            "Loại NV:",
            "Phòng Ban:"));

    public WorkingWithPDF() {

    }

    // Method to create the nested table with no wrap
    private Table createNestedTable(ArrayList<String> data) {
        float[] nestedColumnWidths = { 8, 10, 8, 10 }; // Adjust column widths as needed

        Table nestedTable = new Table(UnitValue.createPointArray(nestedColumnWidths));
        nestedTable.setBorder(Border.NO_BORDER).setMarginLeft(55);
        ;

        // Add rows to the nested table
        for (int i = 0; i < header.size(); i++) {
            String label = header.get(i);
            String value = data.get(i);

            // Set no wrap for each cell
            Cell labelCell = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setHeight(60);
            labelCell.setKeepTogether(true);
            nestedTable.addCell(labelCell);

            Cell valueCell = new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph(value).setTextAlignment(TextAlignment.LEFT)).setHeight(60)
                    .setPaddingRight(30);
            valueCell.setKeepTogether(true);
            nestedTable.addCell(valueCell);
        }

        return nestedTable;
    }

    public void createPDF(ArrayList<String> data) {
        String dest = "src/main/resources/files/output.pdf";
        PdfFont font = null;
        // src/main/resources/files/
        File fontFile = new File("src/main/resources/files/vuArial.ttf");

        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Load a Unicode font that supports Vietnamese characters
            try {
                font = PdfFontFactory.createFont(fontFile.getAbsolutePath(), "Identity-H");
                document.setFont(font);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Create a large, centered title in Vietnamese
            Paragraph title = new Paragraph("THÔNG TIN NHÂN VIÊN")
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Add the image
            String imagePath = "/avatars/" + "test-avatar.png";
            URL location = UserInfoContentPanel.class.getResource(imagePath);
            Image image = new Image(ImageDataFactory.create(location));
            image.setAutoScale(false);
            image.setHeight(100);
            image.setWidth(100);
            image.scaleToFit(100, 100);

            // Define table layout propertiesS
            float[] columnWidths = { 2, 10 }; // Adjust column widths as needed

            Table mainTable = new Table(UnitValue.createPointArray(columnWidths));
            mainTable.setMarginTop(20); // Add some margin after the title

            // Create the first row of the main table with no border
            mainTable.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
            Cell rightCell = new Cell()
                    .add(createNestedTable(data))
                    .setBorder(Border.NO_BORDER);
            mainTable.addCell(rightCell);

            document.add(mainTable);

            document.close();
            JOptionPane.showMessageDialog(null, "PDF file created successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
