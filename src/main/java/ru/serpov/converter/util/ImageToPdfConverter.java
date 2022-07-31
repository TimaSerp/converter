package ru.serpov.converter.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


public class ImageToPdfConverter {
    private static final Logger log = LoggerFactory.getLogger(ImageToPdfConverter.class);
    public void imagesToPdf(File root, String destination, String size) throws IOException, DocumentException {
        int filesCount = 0;
        int countSuccess = 0;
        if (root.isDirectory()) {
            for (File file: Objects.requireNonNull(root.listFiles())) {
                if (file.isFile() && isCorrectType(file) && imageToPdf(file, destination, size)) {
                    countSuccess++;
                }
                filesCount++;
            }
            log.info("Успешно сконвертировано файлов {} из {}", countSuccess, filesCount);
        }
    }

    private boolean isCorrectType(File file) {
        String[] splitFileName = file.getName().split("\\.");
        String fileType = splitFileName[splitFileName.length - 1];
        return fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("gif") || fileType.equals("png");
    }

    private boolean imageToPdf(File file, String destination, String size) throws DocumentException, IOException {
        Document document = createDocument(size);
        if (createDirectory(destination)) {
            File pdfFile = new File(destination, file.getName().split("\\.")[0] + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            log.info("Конвертация началась");
            Image image = Image.getInstance(file.getAbsolutePath());
            document.setPageSize(image);
            document.newPage();
            image.setAbsolutePosition(0, 0);
            document.add(image);
            document.close();
            return true;
        }
        return false;
    }

    private boolean createDirectory(String destination) {
        File dirTo = new File(destination);
        log.info("Создание папки...");
        if (!dirTo.exists()) {
            dirTo.mkdir();
            log.info("Папка создана -> " + dirTo.getAbsolutePath());
        } else if (dirTo.exists() && dirTo.canExecute()) {
            log.info("Папка уже существует");
        } else {
            log.info("Не удалось создать папку");
            return false;
        }
        return true;
    }

    private Document createDocument(String size) {
        return switch (size) {
            case "A1" -> new Document(PageSize.A1);
            case "A2" -> new Document(PageSize.A2);
            case "A3" -> new Document(PageSize.A3);
            case "A4" -> new Document(PageSize.A4);
            case "A5" -> new Document(PageSize.A5);
            case "A6" -> new Document(PageSize.A6);
            case "A7" -> new Document(PageSize.A7);
            case "A8" -> new Document(PageSize.A8);
            case "A9" -> new Document(PageSize.A9);
            case "A10" -> new Document(PageSize.A10);
            default -> null;
        };
    }
}
