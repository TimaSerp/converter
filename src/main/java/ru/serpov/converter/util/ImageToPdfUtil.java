package ru.serpov.converter.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageToPdfUtil {
    private static final Logger log = LoggerFactory.getLogger(ImageToPdfUtil.class);

    public void convertImagesToPdf(@NonNull List<File> images, String pageSize) throws IOException, DocumentException {
        String destination = images.get(0).getParentFile() + "\\pdf";
        int imagesCount = 0;
        int countSuccess = 0;
        List<File> notSuccessFiles = new ArrayList<>();
        if (createDirectory(destination)) {
            for (File image : images) {
                if (image.isFile() && isCorrectType(image)) {
                    if (convertSingleImage(image, destination, pageSize)) {
                        countSuccess++;
                    } else {
                        notSuccessFiles.add(image);
                    }
                    imagesCount++;
                }
            }
            log.info("Successfully converted {} files of {}", countSuccess, imagesCount);
            if (!notSuccessFiles.isEmpty()) {
                log.info("Couldn't convert these files: ");
                for (File file : notSuccessFiles) {
                    log.info(file.getName() + "\n");
                }
            }
        }
    }

    private boolean convertSingleImage(File file, String destination, String pageSize) {
        try {
            Document document = createDocumentOfSize(pageSize);
//            This block is needed for executing Document
            FileOutputStream fileOutputStream = new FileOutputStream(new File(destination, file.getName().split("\\.")[0] + ".pdf"));
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

            document.open();
            log.info("Converting the file: " + file.getName());
            Image image = Image.getInstance(file.getAbsolutePath());
            document.setPageSize(image);
            document.newPage();
            image.setAbsolutePosition(0, 0);
            document.add(image);
            document.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCorrectType(File file) {
        String[] splitFileName = file.getName().split("\\.");
        String fileType = splitFileName[splitFileName.length - 1];
        return fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("gif") || fileType.equals("png");
    }

    private boolean createDirectory(String destination) {
        File dirTo = new File(destination);
        log.info("Creating directory...");
        if (!dirTo.exists()) {
            dirTo.mkdir();
            log.info("Directory created successfully: " + dirTo.getAbsolutePath());
        } else if (dirTo.exists() && dirTo.canExecute()) {
            log.info("Directory already exist");
        } else {
            log.info("Couldn't create directory");
            return false;
        }
        return true;
    }

    private Document createDocumentOfSize(String pageSize) {
        switch (pageSize) {
            case "A1":
                return new Document(PageSize.A1);
            case "A2":
                return new Document(PageSize.A2);
            case "A3":
                return new Document(PageSize.A3);
            case "A4":
                return new Document(PageSize.A4);
            case "A5":
                return new Document(PageSize.A5);
            case "A6":
                return new Document(PageSize.A6);
            case "A7":
                return new Document(PageSize.A7);
            case "A8":
                return new Document(PageSize.A8);
            case "A9":
                return new Document(PageSize.A9);
            case "A10":
                return new Document(PageSize.A10);
            default:
                return null;
        }
    }
}
