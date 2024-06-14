package uz.pdp.back.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.pdp.back.config.entity.Education;
import uz.pdp.back.config.entity.Experience;
import uz.pdp.back.model.FormDataDto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA;
import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD;

@Service
public class FileServiceImpl implements FileService {

    @SneakyThrows
    @Override
    public byte[] generatePdf(FormDataDto fileDataDto, byte[] photo){
        String educationsJson = fileDataDto.getEducations();
        String experiencesJson = fileDataDto.getExperiences();
        String techsJson = fileDataDto.getSelectedTech();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Education> educations = mapper.readValue(educationsJson, new TypeReference<>() {});
        List<Experience> experiences = mapper.readValue(experiencesJson, new TypeReference<>() {});
        List<String> techs = mapper.readValue(techsJson, new TypeReference<>() {});
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Add photo if available
                if (photo != null) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(photo));

                        if (bufferedImage != null) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "png", baos); // Convert to PNG
                            byte[] pngBytes = baos.toByteArray();
                            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, pngBytes, "photo");
                            contentStream.drawImage(pdImage, 25, 700, 100, 100);
                        } else {
                            System.err.println("BufferedImage is null after reading photo byte array");
                        }
                    } catch (IOException e) {
                        System.err.println("Error processing image: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                // Set up fonts
                PDType1Font font1 = new PDType1Font(HELVETICA_BOLD);
                PDType1Font font2 = new PDType1Font(HELVETICA);

                contentStream.setFont(font1, 20);
                contentStream.beginText();
                contentStream.setLeading(20.5f);
                contentStream.newLineAtOffset(150, 750);

                // Personal Information
                contentStream.showText(fileDataDto.getFirstName() + " " + fileDataDto.getLastName());
                contentStream.setFont(font2, 12);
                contentStream.newLine();
                contentStream.showText("Email: " + fileDataDto.getEmail());
                contentStream.newLine();
                contentStream.showText("Phone: " + fileDataDto.getPhone());
                contentStream.newLine();
                contentStream.showText("Age: " + fileDataDto.getAge());
                contentStream.newLine();
                contentStream.newLine();

                // Education Section
                contentStream.setFont(font1, 16);
                contentStream.showText("Education:");
                contentStream.newLine();
                contentStream.setFont(font2, 12);
                for (Education education : educations) {
                    contentStream.showText("School: " + education.getEducationSchool());
                    contentStream.newLine();
                    contentStream.showText("Start Date: " + education.getEducationStartDate());
                    contentStream.newLine();
                    contentStream.showText("End Date: " + education.getEducationEndDate());
                    contentStream.newLine();
                    contentStream.newLine();
                }

                // Experience Section
                contentStream.setFont(font1, 16);
                contentStream.showText("Experience:");
                contentStream.newLine();
                contentStream.setFont(font2, 12);
                for (Experience experience : experiences) {
                    contentStream.showText("Title: " + experience.getExperienceTitle());
                    contentStream.newLine();
                    contentStream.showText("Company: " + experience.getExperienceCompany());
                    contentStream.newLine();
                    contentStream.showText("Description: " + experience.getExperienceDescription());
                    contentStream.newLine();
                    contentStream.showText("Start Date: " + experience.getExperienceStartDate());
                    contentStream.newLine();
                    contentStream.showText("End Date: " + experience.getExperienceEndDate());
                    contentStream.newLine();
                    contentStream.newLine();
                }

                // Technologies Section
                contentStream.setFont(font1, 16);
                contentStream.showText("Technologies:");
                contentStream.newLine();
                contentStream.setFont(font2, 12);
                for (String tech : techs) {
                    contentStream.showText(tech);
                    contentStream.newLine();
                }
                contentStream.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            baos.toByteArray();
            return baos.toByteArray();
        }
    }

    @SneakyThrows
    @Override
    public List<String> getTechs() {
        Workbook workbook = new XSSFWorkbook((System.getProperty("user.dir")+"/techs.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        List<String> techs = new ArrayList<>();
        for (Row row : sheet) {
            if(row.getCell(0).getStringCellValue().equals("Technologies")){ // is a first tow of excel
                continue;
            }
            techs.add(row.getCell(0).getStringCellValue());
        }
        workbook.close();
        return techs;
    }
}
