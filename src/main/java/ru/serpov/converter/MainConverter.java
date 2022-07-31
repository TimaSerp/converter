package ru.serpov.converter;

import ru.serpov.converter.util.ImageToPdfConverter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainConverter {
    public static void main(String[] args) {
        try {
            ImageToPdfConverter converter = new ImageToPdfConverter();
            Scanner scan = new Scanner(System.in);
            System.out.println("Введите путь к папке-источнику:");
            List<String> checkSizeArray = Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");
            while (true) {
//                File root = new File(scan.nextLine());
                // Test data
                File root = new File("C:\\Users\\AnTi\\Documents\\test");
                if (root.exists()) {
                    System.out.println("Введите размер изображения (A1, A2, A3 и т.д.):");
                    while (true) {
//                        String size = scan.nextLine();
                        // Test data
                        String size = "A4";
                        if (!checkSizeArray.contains(size)) {
                            System.out.println("Введите корректный размер изображения(A1, A2, A3 и т.д.):");
                        } else {
                            converter.imagesToPdf(root, root + "\\pdf", size);
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Введите корректный путь:");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
