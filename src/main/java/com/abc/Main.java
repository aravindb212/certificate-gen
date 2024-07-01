package com.abc;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Main {

    @SuppressWarnings("resource")
    public static void generateCertificate(String username, String companyName) throws IOException {
        try (PDDocument document = new PDDocument()) {
            // Create a new page with A4 size in landscape orientation
            PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            document.addPage(page);

            // Load background image (replace with your own background)
            InputStream bgStream = Main.class.getResourceAsStream("/background1.png");
            if (bgStream == null) {
                throw new IOException("Background image not found");
            }
            PDImageXObject background = PDImageXObject.createFromByteArray(document, bgStream.readAllBytes(), "background");
 // Load cursive style font (replace with your cursive font file)
            InputStream fontStream = Main.class.getResourceAsStream("/fonts1/CursiveBold.ttf");
            if (fontStream == null) {
                throw new IOException("Cursive font file not found");
            }
            PDType0Font cursiveFont = PDType0Font.load(document, fontStream);
            // Load logo image
            InputStream logoStream = Main.class.getResourceAsStream("/logo1.jpg");
            if (logoStream == null) {
                throw new IOException("Logo image not found");
            }
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
            InputStream signatureStream = Main.class.getResourceAsStream("/sign.png"); 
            if (signatureStream == null) { 
                throw new IOException("Signature photo not found"); 
            } 
            PDImageXObject signature = PDImageXObject.createFromByteArray(document, signatureStream.readAllBytes(), "signature");
            // Prepare content stream for text rendering
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Draw background image
            contentStream.drawImage(background, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

            // Draw logo image
            float logoWidth = 120;
            float logoHeight = 120;
            contentStream.drawImage(logo, 100, page.getMediaBox().getHeight() - logoHeight - 80, logoWidth, logoHeight);
            // Load stamp image
            InputStream stampStream = Main.class.getResourceAsStream("/stamp1.png");
            if (stampStream == null) {
                throw new IOException("Stamp image not found");
            }
            PDImageXObject stamp = PDImageXObject.createFromByteArray(document, stampStream.readAllBytes(), "stamp");
           
            
            // Title
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 48);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(120, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Certificate of Participation");
            contentStream.endText();
            // Participant's name
            contentStream.setFont(PDType1Font.HELVETICA, 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 300);
            contentStream.showText("This Certificate is proudly presented to");
            contentStream.endText();
            

            // Participant's name
            contentStream.setFont(cursiveFont, 48);
            contentStream.beginText();
            contentStream.newLineAtOffset(300, page.getMediaBox().getHeight() - 345);
            contentStream.setNonStrokingColor(Color.CYAN.darker());
            contentStream.showText( username);
            contentStream.endText();
            // Participant's name
            contentStream.setFont(PDType1Font.HELVETICA, 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 385);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("For participating in the java program Workshop held by ");
            contentStream.newLineAtOffset(30, -20); // Move to the next line
            contentStream.showText("B2M IT Consultancy Services on 19 June 2023");
            contentStream.endText();

            // Decorative line
                
            contentStream.setLineWidth(2);
            contentStream.moveTo(120, page.getMediaBox().getHeight() - 350);
            contentStream.lineTo(page.getMediaBox().getWidth() - 120, page.getMediaBox().getHeight() - 350);
            contentStream.stroke();
            // Company name
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(500, page.getMediaBox().getHeight() - 500);
            contentStream.showText(companyName);
            contentStream.endText();
         
            //  stamp
            float stampWidth = 100;
            float stampHeight = 100;
            contentStream.drawImage(stamp, 550, page.getMediaBox().getHeight() - stampHeight - 430, stampWidth, stampHeight);
               // sign
            float logoWidth1 = 70;
            float logoHeight1 = 70;
            contentStream.drawImage(signature, 600, page.getMediaBox().getHeight() - logoHeight1 - 420, logoWidth1, logoHeight1);
            
            // Close content stream
            contentStream.close();

            // Save the document to an absolute path
            String outputPath = "E:/JAVA_brgv/Projects/output_certificate.pdf"; // Use an absolute path here
            document.save(new File(outputPath));
            System.out.println("Certificate generated successfully at " + outputPath);
        }
    }

    public static void main(String[] args) {
        try {
            generateCertificate("Bhargav Banam", "B2M IT Consultancy Services");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
