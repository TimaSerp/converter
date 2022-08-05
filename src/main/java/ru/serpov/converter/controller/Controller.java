package ru.serpov.converter.controller;

import com.itextpdf.text.DocumentException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import ru.serpov.converter.util.ImageToPdfUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final FileChooser fileChooser = new FileChooser();
    private static final ImageToPdfUtil converter = new ImageToPdfUtil();
    private List<File> files = new ArrayList<>();
    private String pageSize;

    @FXML
    private Button chooseFilesButton;

    @FXML
    private ChoiceBox<String> choosePageSizeChoiceBox;

    @FXML
    private Button convertButton;

    @FXML
    void initialize() {
//        Open page for choosing files
        chooseFilesButton.setOnAction(event -> files = fileChooser.showOpenMultipleDialog(chooseFilesButton.getScene().getWindow()));
//        Set page size (A1, A2, A3 and so on)
        choosePageSizeChoiceBox.setOnAction(event -> pageSize = choosePageSizeChoiceBox.getValue());
//        Do converting
        convertButton.setOnAction(event -> {
            try {
                converter.convertImagesToPdf(files, pageSize);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        });
    }

}
